package com.example.paypalms.repository;

import com.example.paypalms.domain.Transaction;
import com.example.paypalms.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByPaymentId(String paymentId);

    Collection<Transaction> findAllByClientIdAndStatus(long clientId, TransactionStatus status);
}
