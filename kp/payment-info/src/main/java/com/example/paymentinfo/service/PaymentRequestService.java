package com.example.paymentinfo.service;


import com.example.paymentinfo.dto.PaymentRequestDTO;

public interface PaymentRequestService {

    PaymentRequestDTO getPaymentRequest(Long merchantOrderId);

    PaymentRequestDTO getPaymentUrl(PaymentRequestDTO requestDTO);
}
