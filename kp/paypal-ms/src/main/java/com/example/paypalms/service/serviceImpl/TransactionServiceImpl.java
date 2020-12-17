package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.Client;
import com.example.paypalms.domain.Transaction;
import com.example.paypalms.repository.TransactionRepository;
import com.example.paypalms.service.TransactionService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findById(Long id) {
        Transaction found = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Transaction with id {0} does not exist.", id)));
        return found;
    }

    @Override
    public Transaction findByPaymentId(Long paymentId) {
        return transactionRepository.findByPaymentId(paymentId);
    }
}
