package com.example.luservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.luservice.model.PaymentData;


@Repository
public interface PaymentDataRepository extends JpaRepository<PaymentData, Long> {
}

