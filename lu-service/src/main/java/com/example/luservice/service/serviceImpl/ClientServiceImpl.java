package com.example.luservice.service.serviceImpl;

import com.example.luservice.model.Client;
import com.example.luservice.repository.ClientRepository;
import com.example.luservice.service.ClientService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
}
