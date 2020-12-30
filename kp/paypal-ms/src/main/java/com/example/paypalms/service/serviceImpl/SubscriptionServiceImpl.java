package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.Client;
import com.example.paypalms.domain.Subscription;
import com.example.paypalms.enums.SubscriptionStatus;
import com.example.paypalms.repository.SubscriptionRepository;
import com.example.paypalms.service.ClientService;
import com.example.paypalms.service.SubscriptionService;
import com.paypal.api.payments.Agreement;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private ClientService clientService;
    @Value("${execution.mode}")
    private String executionMode;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, ClientService clientService) {
        this.subscriptionRepository = subscriptionRepository;
        this.clientService = clientService;
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
     * Compare subscriptions statues with PayPal every hour
     */
    //@Scheduled(initialDelay = 10000, fixedRate = 3600000)
    //@Scheduled(initialDelay = 10000, fixedRate = 180000)
    @Scheduled(initialDelay = 180000, fixedRate = 300000)
    @Async
    @Override
    public void syncSubscriptions() {
        List<Client> clients = (ArrayList) clientService.getAll();

        clients.stream().forEach(client -> {
            APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);
            List<Subscription> subscriptions = findAllBySellerIdAndSubscriptionStatus(client.getId(), SubscriptionStatus.COMPLETED);

            if (!subscriptions.isEmpty()) {
                for (Subscription subscription : subscriptions) {
                    try {
                        Agreement agreement = Agreement.get(context, subscription.getBillingAgreementId());

                        System.out.println("KP: " + subscription.getSubscriptionStatus() + " | PP: " + agreement.getState());

                        if (agreement.getState().equalsIgnoreCase("ACTIVE")) {
                            subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
                            save(subscription);
                        } else if (agreement.getState().equalsIgnoreCase("CANCELLED")) {
                            subscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
                            save(subscription);
                        } else if (agreement.getState().equalsIgnoreCase("EXPIRED")) {
                            subscription.setSubscriptionStatus(SubscriptionStatus.EXPIRED);
                            save(subscription);
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
}
