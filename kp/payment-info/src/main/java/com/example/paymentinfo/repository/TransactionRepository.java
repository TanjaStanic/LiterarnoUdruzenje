package com.example.paymentinfo.repository;

import com.example.paymentinfo.domain.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.paymentinfo.domain.Transaction;

import java.util.Collection;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByPaymentID(String paymentId);

    Transaction findByMerchantOrderId(long merchantOrderId);

    Collection<Transaction> findAllByStatusIn(Collection<TransactionStatus> statuses);

}
