package com.example.luservice.controller;

import java.util.List;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.luservice.model.PaymentMethod;
import com.example.luservice.service.PaymentService;

@RequestMapping(value = "/api/paymentMethod")
public class PaymentMethodController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping(value = "/{userId}")
    public ResponseEntity showPage(@PathVariable Long userId, @RequestBody List<PaymentMethod> paymentMethods) {

        this.paymentService.newSellerPaymentMethods(paymentMethods, userId);
        return ResponseEntity.ok().build();
    }


}
