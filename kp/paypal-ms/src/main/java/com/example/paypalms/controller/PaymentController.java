package com.example.paypalms.controller;

import com.example.paypalms.domain.Transaction;
import com.example.paypalms.dto.PaymentRequestDTO;
import com.example.paypalms.dto.TransactionDto;
import com.example.paypalms.service.PaymentService;
import com.example.paypalms.service.TransactionService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
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
    private TransactionService transactionService;

    public PaymentController(PaymentService paymentService, RestTemplate restTemplate, TransactionService transactionService) {
        this.paymentService = paymentService;
        this.restTemplate = restTemplate;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody @Valid PaymentRequestDTO paymentRequest) {
        String redirectUrl;
        try {
            redirectUrl = paymentService.createPayment(paymentRequest);
            if (redirectUrl == null) {
                log.error("CANCELED | PayPal Payment | Amount: " + paymentRequest.getAmount() + " " + paymentRequest.getCurrencyCode());
                redirectUrl = paymentRequest.getErrorUrl();
            }
        } catch (PayPalRESTException e) {
            log.error("CANCELED | PayPal Payment | Amount: " + paymentRequest.getAmount() + " " + paymentRequest.getCurrencyCode());
            redirectUrl = paymentRequest.getFailedUrl();
        }

       return ResponseEntity.ok(redirectUrl);
    }

    @GetMapping(value = "/finish")
    public ResponseEntity<?> finishPayment(@RequestParam String paymentId, @RequestParam String token, @RequestParam String PayerID) {
        String redirectUrl = paymentService.finishPayment(paymentId, PayerID);

        if (redirectUrl != null) {
            String responseUrl = sendPaymentResponse(paymentId, redirectUrl);
            if (responseUrl != null){
                HttpHeaders headersRedirect = new HttpHeaders();
                headersRedirect.add("Location", responseUrl);
                headersRedirect.add("Access-Control-Allow-Origin", "*");
                return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
            }
        }
        log.error("ERROR | TRANSACTION WITH PAYMENT ID: " + paymentId + " NOT FOUND");
        return ResponseEntity.badRequest().build();

    }

    @GetMapping(value = "/cancel")
    public @ResponseBody
    ResponseEntity<?> cancelPayment(@RequestParam Long transactionId) {
        String redirectUrl = paymentService.cancelPayment(transactionId);
        if (redirectUrl != null) {
            try {
                Transaction transaction = transactionService.findById(transactionId);
                HttpEntity<TransactionDto> entity = new HttpEntity<>(new TransactionDto(transaction));
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(redirectUrl, entity, String.class);
                if (responseEntity.getStatusCode() == HttpStatus.OK){
                    HttpHeaders headersRedirect = new HttpHeaders();
                    headersRedirect.add("Location", responseEntity.getBody());
                    headersRedirect.add("Access-Control-Allow-Origin", "*");
                    return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                log.error(exception);
            }

        }
        log.error("ERROR | TRANSACTION WITH ID: " + transactionId + " NOT FOUND");
        return ResponseEntity.badRequest().build();
    }

    private String sendPaymentResponse(String paymentId, String url) {
        Transaction transaction = null;
        try {
            transaction = transactionService.findByPaymentId(paymentId);
            HttpEntity<TransactionDto> entity = new HttpEntity<>(new TransactionDto(transaction));
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK){
                return responseEntity.getBody();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(exception);
        }
        return null;
    }
}
