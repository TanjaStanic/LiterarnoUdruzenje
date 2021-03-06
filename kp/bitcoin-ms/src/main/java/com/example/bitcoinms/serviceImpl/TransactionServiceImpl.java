package com.example.bitcoinms.serviceImpl;

import java.text.MessageFormat;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.bitcoinms.domain.Transaction;
import com.example.bitcoinms.enums.TransactionStatus;
import com.example.bitcoinms.repository.TransactionRepository;
import com.example.bitcoinms.service.TransactionService;

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

    /*@Override
    public Transaction findByPaymentID(long paymentId) {
        return transactionRepository.findByPaymentID(paymentId);
    }*/

    @Override
    public Set<Transaction> findAllByMerchantOrderId(long merchantOrderId) {
        return transactionRepository.findAllByMerchantOrderId(merchantOrderId);
    }

    @Override
    public Set<Transaction> findAllBySellerId(long sellerId) {
        return transactionRepository.findAllBySellerId(sellerId);
    }

    @Override
    public Set<Transaction> findAllBySellerIdAndStatus(long sellerId, TransactionStatus status) {
        return findAllBySellerIdAndStatus(sellerId, status);
    }

    @Override
    public Transaction findById(long id) {
        Transaction found = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Transaction with id {0} does not exist.", id)));
        return found;
    }


}