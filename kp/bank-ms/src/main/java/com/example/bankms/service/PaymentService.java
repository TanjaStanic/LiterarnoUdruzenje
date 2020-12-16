package com.example.bankms.service;

import com.example.bankms.dto.CompletedPaymentDTO;
import com.example.bankms.dto.PaymentRequestDTO;

public interface PaymentService {

    String initiatePayment(PaymentRequestDTO request);

    void completePayment(CompletedPaymentDTO completedPayment);
}
