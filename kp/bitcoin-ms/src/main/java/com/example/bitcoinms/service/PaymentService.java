package com.example.bitcoinms.service;


import com.example.bitcoinms.dto.PaymentRequestDTO;

public interface PaymentService {

    String initiatePayment(PaymentRequestDTO request);

    String cancelPayment(Long id);

    String paymentSuccess(Long id);
}
