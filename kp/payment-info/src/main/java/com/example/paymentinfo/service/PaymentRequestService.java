package com.example.paymentinfo.service;

import org.springframework.stereotype.Service;

import com.example.paymentinfo.dto.PaymentRequestDTO;

@Service
public interface PaymentRequestService {
	    PaymentRequestDTO getPaymentRequest(Long merchantOrderId);
	    PaymentRequestDTO getPaymentUrl(PaymentRequestDTO requestDTO);
}
