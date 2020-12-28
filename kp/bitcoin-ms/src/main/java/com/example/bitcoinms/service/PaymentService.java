package com.example.bitcoinms.service;

import org.springframework.stereotype.Service;

import com.example.bitcoinms.dto.PaymentRequestDTO;

@Service
public interface PaymentService {
	
	String initiatePayment(PaymentRequestDTO request);


}
