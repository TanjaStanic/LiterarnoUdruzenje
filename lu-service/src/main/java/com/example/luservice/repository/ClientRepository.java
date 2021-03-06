package com.example.luservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.luservice.model.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientById(Long id);

    Client findClientByUserId(Long userId);

    Client findByEmail(String email);



}
