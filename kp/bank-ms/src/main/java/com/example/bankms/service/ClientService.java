package com.example.bankms.service;

import com.example.bankms.domain.Client;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface ClientService {
    Client insert(Client client);

    Client update(Client updated);

    void delete(Client client);

    Client findByEmail(String email);

    Client findById(Long id);

    void deleteById(Long id);

    Set<Client> getAll();

}
