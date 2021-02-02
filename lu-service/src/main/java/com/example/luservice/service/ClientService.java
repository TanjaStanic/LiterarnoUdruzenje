package com.example.luservice.service;

import com.example.luservice.model.Client;

public interface ClientService {
    Client findByEmail(String email);

    Client findById(Long id);
}
