package com.example.paypalms.controller;

import com.example.paypalms.dto.PaymentRequestDTO;
import com.example.paypalms.service.PaymentService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
@Log4j2
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        String redirectUrl;
        try {
            redirectUrl = paymentService.createPayment(paymentRequest);
            if (redirectUrl == null) {
                log.error("CANCELED | PayPal Payment | Amount: " + paymentRequest.getAmount() + " " + paymentRequest.getCurrency());
                return ResponseEntity.badRequest().build();
            }
        } catch (PayPalRESTException e) {
            log.error("CANCELED | PayPal Payment | Amount: " + paymentRequest.getAmount() + " " + paymentRequest.getCurrency());
            return ResponseEntity.badRequest().build();
        }
        log.info("COMPLETED | PayPal Payment | Amount: " + +paymentRequest.getAmount() + " " + paymentRequest.getCurrency());

        return ResponseEntity.ok(redirectUrl);
    }

    @PostMapping("/finish")
    public ResponseEntity<?> finishPayment(@RequestParam String paymentId, @RequestParam String PayerID) {
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

    @GetMapping("/cancel")
    public ResponseEntity<?> cancelPayment(@RequestParam Long transactionId) {
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
