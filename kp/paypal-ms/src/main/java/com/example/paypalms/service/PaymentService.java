package com.example.paypalms.service;


import com.example.paypalms.dto.PaymentRequestDTO;
import com.paypal.base.rest.PayPalRESTException;

public interface PaymentService {

    String createPayment(PaymentRequestDTO paymentRequest) throws PayPalRESTException;

    String finishPayment(String paymentId, String PayerID);

    String cancelPayment(Long transactionId);
}
