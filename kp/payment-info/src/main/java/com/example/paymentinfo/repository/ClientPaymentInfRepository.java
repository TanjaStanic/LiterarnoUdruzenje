package com.example.paymentinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paymentinfo.domain.ClientPaymentInformation;


public interface ClientPaymentInfRepository extends JpaRepository<ClientPaymentInformation, Long> {

}
