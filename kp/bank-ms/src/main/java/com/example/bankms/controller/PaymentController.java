package com.example.bankms.controller;

import com.example.bankms.dto.CompletedPaymentDTO;
import com.example.bankms.dto.PaymentRequestDTO;
import com.example.bankms.service.ClientService;
import com.example.bankms.service.PaymentService;
import com.example.bankms.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate-payment")
    public ResponseEntity<?> createPaymentTransaction(@RequestBody PaymentRequestDTO request) {
        try{
            String response = paymentService.initiatePayment(request);
            return ResponseEntity.ok().body(response);
        }catch (Exception exception){
            return ResponseEntity.badRequest().body("Transaction failed.");
        }
    }

    @PostMapping("/complete-payment")
    public ResponseEntity<?> completePayment(@RequestBody CompletedPaymentDTO completedPayment){
        paymentService.completePayment(completedPayment);
        return ResponseEntity.ok().build();
    }
}
