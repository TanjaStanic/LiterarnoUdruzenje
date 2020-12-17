package com.example.paypalms.repository;

import com.example.paypalms.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByPaymentId(Long paymentId);
}
