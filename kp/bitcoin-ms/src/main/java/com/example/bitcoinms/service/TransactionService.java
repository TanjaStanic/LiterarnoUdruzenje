package com.example.bitcoinms.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.bitcoinms.domain.Transaction;
import com.example.bitcoinms.enums.TransactionStatus;

@Service
public interface TransactionService {

    Transaction save(Transaction transaction);

    // Transaction findByPaymentID(long paymentId);

    Set<Transaction> findAllByMerchantOrderId(long merchantOrderId);

    Set<Transaction> findAllBySellerId(long sellerId);

    Set<Transaction> findAllBySellerIdAndStatus(long sellerId, TransactionStatus status);

    Transaction findById(long id);

}
