package com.example.paymentinfo.service.serviceImpl;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.repository.PaymentMethodRepository;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.PaymentMethodService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private PaymentMethodRepository paymentMethodRepository;
    private ClientService clientService;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository, ClientService clientService) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.clientService = clientService;
    }

    @Override
    public Collection<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod save(PaymentMethod entity) {
        return paymentMethodRepository.save(entity);
    }

    @Override
    public PaymentMethod findById(long id) {
        PaymentMethod found = paymentMethodRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Payment method with id {0} does not exist.", id)));
        return found;
    }

    @Override
    public void delete(PaymentMethod entity) {
        paymentMethodRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        PaymentMethod found = this.findById(id);
        Set<Client> clients = new HashSet<>(found.getClients());
        Iterator it = found.getClients().iterator();
        while (it.hasNext())
        {
            Client item = (Client) it.next();
            it.remove();
            item.removePaymentMethod(found);

        }
        clientService.saveRange(clients);
        paymentMethodRepository.deleteById(id);
    }

    @Override
    public PaymentMethod update(PaymentMethod entity) {
        PaymentMethod found = this.findById(entity.getId());
        entity.setClients(found.getClients());
        return this.save(entity);
    }

    @Override
    public Collection<PaymentMethod> findAllBySubscriptionSupportedIsTrueAndClientsEmail(String email) {
        return paymentMethodRepository.findAllBySubscriptionSupportedIsTrueAndClientsEmail(email);
    }

	@Override
	public void deleteOneFromUser(Long userId, Long paymentMethodId) {
		try {
			Client client = clientService.findById(userId);
			PaymentMethod paymentMethod = paymentMethodRepository.findOneById(paymentMethodId);
			client.removePaymentMethod(paymentMethod);
			paymentMethod.removeClient(client);
			
			client = clientService.save(client);
			paymentMethod = paymentMethodRepository.save(paymentMethod);
			
		} catch (Exception e) {
			System.out.println("Cant find entities.");
		}
		
	}

	@Override
	public List<PaymentMethod> findAllNoSupportedByClient(Long id) {
		List<PaymentMethod> allMethods = new ArrayList<PaymentMethod>();
		List<PaymentMethod> tempList = new ArrayList<PaymentMethod>();
		List<PaymentMethod> returnList = new ArrayList<PaymentMethod>();

		try {
			allMethods = paymentMethodRepository.findAll();
			tempList = paymentMethodRepository.findAllByClients_id(id);
		} catch (Exception e) {
			System.out.println("Cant find entities.");
		}
		for (PaymentMethod p : allMethods) {
			if (!tempList.contains(p)) {
				returnList.add(p);
			}
		}
		
		return returnList;
	}

	@Override
	public void addPaymetMethodToClient(Long userId, Long paymentMethodId) {
		try {
			Client client = clientService.findById(userId);
			PaymentMethod paymentMethod = paymentMethodRepository.findOneById(paymentMethodId);
			client.addPaymentMethod(paymentMethod);
			paymentMethod.addClient(client);
			
			client = clientService.save(client);
			paymentMethod = paymentMethodRepository.save(paymentMethod);
			
		} catch (Exception e) {
			System.out.println("Cant find entities.");
		}
		
	}
}
