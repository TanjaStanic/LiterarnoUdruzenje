package com.example.bitcoinms.controller;


import com.example.bitcoinms.domain.Transaction;
import com.example.bitcoinms.dto.TransactionDto;
import com.example.bitcoinms.service.TransactionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bitcoinms.dto.PaymentRequestDTO;
import com.example.bitcoinms.service.PaymentService;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity<byte[]> cancelPayment(@RequestParam Long id) {
        String redirectUrl = paymentService.cancelPayment(id);

        if (redirectUrl != null) {
           String responseUrl = sendPaymentResponse(id, redirectUrl);
            if (responseUrl != null){
                HttpHeaders headersRedirect = new HttpHeaders();
                headersRedirect.add("Location", responseUrl);
                headersRedirect.add("Access-Control-Allow-Origin", "*");
                return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/success")
    public ResponseEntity<byte[]> paymentSuccess(@RequestParam Long id) {
        String redirectUrl = paymentService.paymentSuccess(id);

        if (redirectUrl != null) {
            String responseUrl = sendPaymentResponse(id, redirectUrl);
            if (responseUrl != null){
                HttpHeaders headersRedirect = new HttpHeaders();
                headersRedirect.add("Location", responseUrl);
                headersRedirect.add("Access-Control-Allow-Origin", "*");
                return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
            }

        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/callback")
    public ResponseEntity<byte[]> paymentCallback(@RequestParam Long id) {
        String redirectUrl = paymentService.paymentSuccess(id);
        if (redirectUrl != null) {
            String responseUrl = sendPaymentResponse(id, redirectUrl);
            if (responseUrl != null){
                HttpHeaders headersRedirect = new HttpHeaders();
                headersRedirect.add("Location", responseUrl);
                headersRedirect.add("Access-Control-Allow-Origin", "*");
                return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    private String sendPaymentResponse(long transactionId, String url) {
        Transaction transaction = null;
        try {
            transaction = this.transactionService.findById(transactionId);
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
