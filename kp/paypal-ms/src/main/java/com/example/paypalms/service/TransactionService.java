package com.example.paypalms.service;

import com.example.paypalms.domain.Transaction;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Transaction findById(Long id);

    Transaction findByPaymentId(Long paymentId);
}
