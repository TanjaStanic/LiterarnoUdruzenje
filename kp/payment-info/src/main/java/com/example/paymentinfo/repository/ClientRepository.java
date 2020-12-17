package com.example.paymentinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.paymentinfo.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
