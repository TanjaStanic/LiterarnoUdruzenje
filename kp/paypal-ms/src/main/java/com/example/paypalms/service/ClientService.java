package com.example.paypalms.service;

import com.example.paypalms.domain.Client;

import java.util.Collection;
import java.util.Set;

public interface ClientService {

    Client insert(Client client);

    Client update(Client updated);

    void delete(Client client);

    Client findByEmail(String email);

    Client findById(Long id);

    void deleteById(Long id);

    Collection<Client> getAll();
}
