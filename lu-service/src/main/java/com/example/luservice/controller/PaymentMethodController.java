package com.example.luservice.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.luservice.model.PaymentMethod;
import com.example.luservice.service.PaymentService;

@RestController
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
