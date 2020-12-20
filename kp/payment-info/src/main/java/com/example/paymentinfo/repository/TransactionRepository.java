package com.example.paymentinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.paymentinfo.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByPaymentID(String paymentId);

    Transaction findByMerchantOrderId(long merchantOrderId);

}
