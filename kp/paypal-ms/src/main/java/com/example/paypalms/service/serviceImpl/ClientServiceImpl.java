package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.Client;
import com.example.paypalms.repository.ClientRepository;
import com.example.paypalms.service.ClientService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
    public Set<Client> getAll() {
        return (Set<Client>) clientRepository.findAll();
    }
}
