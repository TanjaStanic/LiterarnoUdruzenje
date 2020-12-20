package com.example.paymentinfo.service;

import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.dto.PaymentRequestDTO;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Transaction findByPaymentID(String paymentId);
    
    Transaction initializeTransaction(PaymentRequestDTO pReqDTO);
}
