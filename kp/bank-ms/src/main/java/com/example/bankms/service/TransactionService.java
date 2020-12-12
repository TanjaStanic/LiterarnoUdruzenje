package com.example.bankms.service;

import com.example.bankms.domain.Transaction;
import com.example.bankms.enums.TransactionStatus;

import java.util.Set;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Transaction findByPaymentID(long paymentId);

    Set<Transaction> findAllByMerchantOrderId(long merchantOrderId);

    Set<Transaction> findAllBySellerId(long sellerId);

    Set<Transaction> findAllBySellerIdAndStatus(long sellerId, TransactionStatus status);

}
