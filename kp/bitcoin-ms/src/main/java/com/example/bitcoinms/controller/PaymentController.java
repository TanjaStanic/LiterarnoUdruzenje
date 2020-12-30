package com.example.bitcoinms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bitcoinms.dto.PaymentRequestDTO;
import com.example.bitcoinms.service.PaymentService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class PaymentController {
	
	private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    
    @PostMapping
    public ResponseEntity<?> createPaymentTransaction(@RequestBody PaymentRequestDTO request) {
        try{
            String response = paymentService.initiatePayment(request);
            // response je url fronta bankacq gde kupac unosi podatke o kartici
            // i taj link treba da se salje literarnom udruzenju
            return ResponseEntity.ok().body(response);
        }catch (Exception exception){
            return ResponseEntity.badRequest().body("Transaction failed.");
        }
    }




}
