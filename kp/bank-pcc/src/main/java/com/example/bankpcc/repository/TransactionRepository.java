package com.example.bankpcc.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankpcc.domain.Transaction;
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	Transaction findOneById(Long id);
}
