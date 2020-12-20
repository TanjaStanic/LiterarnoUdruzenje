package com.example.paymentinfo.service;

import com.example.paymentinfo.domain.Client;

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
