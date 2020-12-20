package com.example.paymentinfo.service.serviceImpl;

import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.repository.TransactionRepository;
import com.example.paymentinfo.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findByPaymentID(String paymentId) {
        return transactionRepository.findByPaymentID(paymentId);
    }
}
