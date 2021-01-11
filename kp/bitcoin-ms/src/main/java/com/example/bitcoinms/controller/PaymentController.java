package com.example.bitcoinms.controller;


import com.example.bitcoinms.service.TransactionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bitcoinms.dto.PaymentRequestDTO;
import com.example.bitcoinms.service.PaymentService;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@RestController
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class PaymentController {

    private final PaymentService paymentService;
    private TransactionService transactionService;
    private RestTemplate restTemplate;

    public PaymentController(PaymentService paymentService, TransactionService transactionService, RestTemplate restTemplate) {
        this.paymentService = paymentService;
        this.transactionService = transactionService;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<?> createPaymentTransaction(@RequestBody PaymentRequestDTO request) {
        try {
            String response = paymentService.initiatePayment(request);
            // response je url fronta bankacq gde kupac unosi podatke o kartici
            // i taj link treba da se salje literarnom udruzenju
            return ResponseEntity.ok().body(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Transaction failed.");
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> cancelPayment(@RequestParam Long id) {
        String redirectUrl = paymentService.cancelPayment(id);

        if (redirectUrl != null) {
            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", redirectUrl);
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

        }
        return ResponseEntity.badRequest()
                .body("An error occurred while trying to cancel payment!");

    }

    @GetMapping("/success")
    public ResponseEntity<?> paymentSuccess(@RequestParam Long id) {
        String redirectUrl = paymentService.paymentSuccess(id);

        if (redirectUrl != null) {
            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", redirectUrl);
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
        }
        return ResponseEntity.badRequest()
                .body("An error occurred.");
    }

    @GetMapping("/callback")
    public ResponseEntity<?> paymentCallback(@RequestParam Long id) {
        String redirectUrl = paymentService.paymentSuccess(id);
        if (redirectUrl != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest()
                .body("An error occurred.");
    }
}
