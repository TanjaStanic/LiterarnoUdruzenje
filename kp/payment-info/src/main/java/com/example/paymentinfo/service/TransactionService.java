package com.example.paymentinfo.service;

import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.domain.TransactionStatus;
import com.example.paymentinfo.dto.PaymentRequestDTO;

import java.util.Collection;

public interface TransactionService {

    Transaction save(Transaction transaction);

    Transaction findByPaymentID(String paymentId);
    
    Transaction initializeTransaction(PaymentRequestDTO pReqDTO);

    Transaction findByMerchantOrderId(long merchantOrderId);

    Transaction findById(long id);

    Collection<Transaction> findAllByStatusIn(Collection<TransactionStatus> statuses);

    void saveRange( Collection<Transaction> transactions);
}
