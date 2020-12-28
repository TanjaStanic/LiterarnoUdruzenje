package com.example.bitcoinms.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.bitcoinms.domain.Client;

@Service
public interface ClientService {
    Client insert(Client client);

    Client update(Client updated);

    void delete(Client client);

    Client findByEmail(String email);

    Client findById(Long id);

    void deleteById(Long id);

    Set<Client> getAll();

}


