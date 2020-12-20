package com.example.bankms.controller;

import com.example.bankms.dto.CompletedPaymentDTO;
import com.example.bankms.dto.PaymentRequestDTO;
import com.example.bankms.service.ClientService;
import com.example.bankms.service.PaymentService;
import com.example.bankms.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    @PostMapping("/complete-payment")
    public ResponseEntity<?> completePayment(@RequestBody CompletedPaymentDTO completedPayment){
        paymentService.completePayment(completedPayment);
        return ResponseEntity.ok().body(completedPayment.getTransactionStatus().toString());
    }
}
