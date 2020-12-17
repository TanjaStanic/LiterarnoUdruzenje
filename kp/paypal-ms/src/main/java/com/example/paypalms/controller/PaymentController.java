package com.example.paypalms.controller;

import com.example.paypalms.dto.PaymentRequestDTO;
import com.example.paypalms.service.PaymentService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class PaymentController {

    private PaymentService paymentService;
    private RestTemplate restTemplate;

    public PaymentController(PaymentService paymentService, RestTemplate restTemplate) {
        this.paymentService = paymentService;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody @Valid PaymentRequestDTO paymentRequest) {
        String redirectUrl;
        try {
            redirectUrl = paymentService.createPayment(paymentRequest);
            if (redirectUrl == null) {
                log.error("CANCELED | PayPal Payment | Amount: " + paymentRequest.getAmount() + " " + paymentRequest.getCurrencyCode());
                return ResponseEntity.badRequest().build();
            }
        } catch (PayPalRESTException e) {
            log.error("CANCELED | PayPal Payment | Amount: " + paymentRequest.getAmount() + " " + paymentRequest.getCurrencyCode());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(redirectUrl);
    }

    @GetMapping(value = "/finish")
    public ResponseEntity<?> finishPayment(@RequestParam String paymentId, @RequestParam String token, @RequestParam String PayerID) {
        String redirectUrl = paymentService.finishPayment(paymentId, PayerID);
        if (redirectUrl == null) {
            log.error("ERROR | TRANSACTION WITH PAYMENT ID: " + paymentId + " NOT FOUND");
            return ResponseEntity.badRequest().build();
        }

        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", redirectUrl);
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

    }

    @GetMapping(value = "/cancel")
    public @ResponseBody ResponseEntity<?> cancelPayment(@RequestParam Long transactionId) {
        String redirectUrl = paymentService.cancelPayment(transactionId);
        if (redirectUrl == null) {
            return ResponseEntity.badRequest().build();
        }
        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", redirectUrl);
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
    }
}
