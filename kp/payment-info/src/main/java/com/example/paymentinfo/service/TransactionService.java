package com.example.paymentinfo.service;

import com.example.paymentinfo.domain.Transaction;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Transaction findByPaymentID(String paymentId);
}
