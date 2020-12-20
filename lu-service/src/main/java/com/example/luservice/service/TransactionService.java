package com.example.luservice.service;

import org.springframework.stereotype.Service;

import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.model.Transaction;

@Service
public interface TransactionService {
	
	Transaction initializeTransaction(PaymentRequestDTO pReqDTO);	
}
