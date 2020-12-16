package com.example.paymentinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.paymentinfo.domain.ClientPaymentInformation;

@Repository
public interface ClientPaymentInfRepository extends JpaRepository<ClientPaymentInformation, Long> {

}
