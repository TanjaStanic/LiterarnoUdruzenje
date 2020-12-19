package com.example.luservice.service;

import org.springframework.stereotype.Service;

import com.example.luservice.model.PaymentRequest;

@Service
public interface PaymentRequestService {
	PaymentRequest getPaymentRequest(Long merchantOrderId);
	PaymentRequest createPaymentRequest(String clientID, double amount);
}
