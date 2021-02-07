package com.example.paymentinfo.service.serviceImpl;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.repository.ClientRepository;
import com.example.paymentinfo.repository.PaymentMethodRepository;
import com.example.paymentinfo.service.ClientService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private PaymentMethodRepository paymentMethodRepository;

    public ClientServiceImpl(ClientRepository clientRepository, PaymentMethodRepository paymentMethodRepository) {
		super();
		this.clientRepository = clientRepository;
		this.paymentMethodRepository = paymentMethodRepository;
	}

    @Override
    public Client insert(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client updated) {
        findById(updated.getId());
        return clientRepository.save(updated);
    }

    @Override
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client findById(Long id) {
        Client found = clientRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Client with id {0} does not exist.", id)));
        return found;
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Collection<Client> getAll() {
        return  clientRepository.findAll();
    }

    @Override
    public Client findByToken(String token) {
        return clientRepository.findByToken(token);
    }

    @Override
    public Client findByUsername(String username) {
        return clientRepository.findByEmail(username);
    }

    @Override
    public void saveRange(Collection<Client> clients) {
        clientRepository.saveAll(clients);
    }

	@Override
	public Client save(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public Client updatePaymentMethod(String paymentMethodName, String email) {
		Client client = clientRepository.findByEmail(email);
		PaymentMethod paymentMethod = paymentMethodRepository.findByName(paymentMethodName);

		client.addPaymentMethod(paymentMethod);
		paymentMethod.addClient(client);

		clientRepository.save(client);
		paymentMethodRepository.save(paymentMethod);

		return client;
	}
}
