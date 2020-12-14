package com.example.bankacquirer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankacquirer.domain.PaymentConcentratorRequest;

public interface PcRequestRepository extends JpaRepository<PaymentConcentratorRequest, Long> {

}
