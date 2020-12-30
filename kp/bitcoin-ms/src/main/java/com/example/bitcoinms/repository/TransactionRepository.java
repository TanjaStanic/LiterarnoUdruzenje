package com.example.bitcoinms.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bitcoinms.domain.Transaction;
import com.example.bitcoinms.enums.TransactionStatus;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	    //Transaction findByPaymentID(long paymentId);

	    Set<Transaction> findAllByMerchantOrderId(Long merchantOrderId);

	    Set<Transaction> findAllBySellerId(Long sellerId);

	    Set<Transaction> findAllBySellerIdAndStatus(Long sellerId, TransactionStatus status);
}
