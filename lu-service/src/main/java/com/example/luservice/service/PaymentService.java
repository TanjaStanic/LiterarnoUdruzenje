package com.example.luservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.luservice.model.PaymentMethod;

@Service
public interface PaymentService {
    boolean newSellerPaymentMethods(List<PaymentMethod> paymentMethods, Long userId);


}
