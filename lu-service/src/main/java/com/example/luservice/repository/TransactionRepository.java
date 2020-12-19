package com.example.luservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.luservice.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Transaction findByMerchantOrderId(Long merchantOrderId);
}


