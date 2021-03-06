package com.example.paymentinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.paymentinfo.domain.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByEmail(String email);

    Client findByToken(String token);

}
