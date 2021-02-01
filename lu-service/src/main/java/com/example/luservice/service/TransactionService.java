package com.example.luservice.service;


import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.model.Transaction;


public interface TransactionService {

    Transaction initializeTransaction(PaymentRequestDTO pReqDTO);

    Transaction findByMerchantOrderId(long merchantOrderId);

    Transaction save(Transaction transaction);
}
