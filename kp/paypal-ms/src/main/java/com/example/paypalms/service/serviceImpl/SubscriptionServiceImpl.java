package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.Client;
import com.example.paypalms.domain.Subscription;
import com.example.paypalms.dto.UserSubscriptionDto;
import com.example.paypalms.enums.SubscriptionStatus;
import com.example.paypalms.repository.SubscriptionRepository;
import com.example.paypalms.service.ClientService;
import com.example.paypalms.service.SubscriptionService;
import com.paypal.api.payments.Agreement;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Log4j2
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private ClientService clientService;
    @Value("${execution.mode}")
    private String executionMode;
    private RestTemplate restTemplate;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, ClientService clientService, RestTemplate restTemplate) {
        this.subscriptionRepository = subscriptionRepository;
        this.clientService = clientService;
        this.restTemplate = restTemplate;
    }

    @Override
    public Collection<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription save(Subscription entity) {
        return subscriptionRepository.save(entity);
    }

    @Override
    public Subscription findById(long id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Subscription with id {0} does not exist.", id)));
    }

    @Override
    public void delete(Subscription entity) {
        subscriptionRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public Subscription update(Subscription entity) {
        return null;
    }

    @Override
    public List<Subscription> findAllBySellerIdAndSubscriptionStatus(long sellerId, SubscriptionStatus status) {
        return (List<Subscription>) subscriptionRepository.findAllBySellerIdAndSubscriptionStatus(sellerId, status);
    }

    /**
     * Sync subscription statues with PayPal every half hour
     */
    //@Scheduled(initialDelay = 10000, fixedRate = 1800000)
    @Scheduled(initialDelay = 180000, fixedRate = 300000)
    @Async
    @Override
    public void syncSubscriptions() {
        List<Client> clients = (ArrayList) clientService.getAll();

        clients.stream().forEach(client -> {
            APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);
            List<Subscription> subscriptions = findAllBySellerIdAndSubscriptionStatus(client.getId(), SubscriptionStatus.ACTIVE);

            if (!subscriptions.isEmpty()) {
                for (Subscription subscription : subscriptions) {
                    try {
                        Agreement agreement = Agreement.get(context, subscription.getBillingAgreementId());

                        System.out.println("KP: " + subscription.getSubscriptionStatus() + " | PP: " + agreement.getState());

                        boolean statusChanged = false;
                        if (agreement.getState().equalsIgnoreCase("CANCELLED")) {
                            subscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
                            save(subscription);
                            statusChanged = true;
                        } else if (agreement.getState().equalsIgnoreCase("EXPIRED")) {
                            subscription.setSubscriptionStatus(SubscriptionStatus.EXPIRED);
                            save(subscription);
                            statusChanged = true;
                        } else if (agreement.getState().equalsIgnoreCase("SUSPENDED")) {
                            subscription.setSubscriptionStatus(SubscriptionStatus.SUSPENDED);
                            save(subscription);
                            statusChanged = true;
                        }


                        if (statusChanged){
                            sendSubscriptionUpdate(subscription);
                        }
                    } catch (PayPalRESTException e) {
                        //subscription doesn't exist
                        if (e.getResponsecode() == 404) {
                            subscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
                            save(subscription);
                        }
                    }
                }
            }
        });
    }

    private void sendSubscriptionUpdate(Subscription subscription){
        UserSubscriptionDto subscriptionDto = new UserSubscriptionDto( subscription.getSeller().getEmail(), subscription.getMerchantOrderId(),
                subscription.getSubscriptionStatus(), subscription.getExpirationDate());
        try {
            HttpEntity<UserSubscriptionDto> entity = new HttpEntity<>(subscriptionDto);
            restTemplate.exchange("https://payment-info/auth/subscriptions/update", HttpMethod.POST, entity, String.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("ERROR | Could not contact payment-info.");
            log.error(exception.getMessage());
        }
    }
}
