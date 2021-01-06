package com.example.bankacquirer.serviceImpl;

import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.dto.NewClientDto;
import com.example.bankacquirer.repository.ClientRepository;
import com.example.bankacquirer.service.ClientService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client insert(NewClientDto newClient) {
        Client client = clientRepository.findByEmail(newClient.getEmail());
        if (client != null) {
            throw new RuntimeException("Client already exists");
        }
        client = new Client();
        // TODO generate merchant_id and merchant_password
        //TODO create new Account and Credit card
        client.setMerchantID("test");
        client.setMerchantPassword("test");
        client.setActive(true);
        client.setName(newClient.getName());
        client = clientRepository.save(client);
        return client;
    }


}
