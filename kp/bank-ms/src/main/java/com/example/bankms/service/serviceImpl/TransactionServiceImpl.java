package com.example.bankms.service.serviceImpl;

import com.example.bankms.domain.Transaction;
import com.example.bankms.enums.TransactionStatus;
import com.example.bankms.repository.TransactionRepository;
import com.example.bankms.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.Set;

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
    public Transaction findByPaymentID(long paymentId) {
        return transactionRepository.findByPaymentID(paymentId);
    }

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
}
