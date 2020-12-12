package com.example.bankms.repository;

import com.example.bankms.domain.Transaction;
import com.example.bankms.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByPaymentID(long paymentId);

    Set<Transaction> findAllByMerchantOrderId(Long merchantOrderId);

    Set<Transaction> findAllBySellerId(Long sellerId);

    Set<Transaction> findAllBySellerIdAndStatus(Long sellerId, TransactionStatus status);
}
