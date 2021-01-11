package com.example.luservice.controller;

import com.example.luservice.model.SubscriptionStatus;
import com.example.luservice.model.User;
import com.example.luservice.model.UserSubscription;
import com.example.luservice.repository.SubscriptionRepository;

import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.service.PaymentService;
import com.example.luservice.service.TransactionService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", maxAge = 3600)
public class PaymentController {

    private PaymentService paymentService;
    private TransactionService transactionService;
    private RestTemplate restTemplate;
    private SubscriptionRepository subscriptionRepository;

    public PaymentController(PaymentService paymentService, TransactionService transactionService, RestTemplate restTemplate, SubscriptionRepository subscriptionRepository) {
        this.paymentService = paymentService;
        this.transactionService = transactionService;
        this.restTemplate = restTemplate;
        this.subscriptionRepository = subscriptionRepository;
    }

    @PostMapping(value = "/confirm-order")
    public ResponseEntity<?> createPaymentRequest(String clientID, double amount) {

        //kada kupac kliknee na kupi
        //treba da mu se otvori stranica sa pay info gdje birra metod placanja
        //pay info salje podatke o prodavcu i iznosu,
        PaymentRequestDTO pReq = paymentService.createPaymentRequest(clientID, amount);

        //cuva transakciju
        transactionService.initializeTransaction(pReq);

        return new ResponseEntity<>(pReq, HttpStatus.OK);
    }

    @GetMapping("auth/bankSuccess")
    public ResponseEntity<?> testBankSuccess() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        requestDTO.setMerchantEmail("test@gmail.com");
        requestDTO.setSuccessUrl("https://localhost:8447/view/success");
        requestDTO.setFailedUrl("https://localhost:8447/view/failed");
        requestDTO.setErrorUrl("https://localhost:8447/view/error");
        requestDTO.setAmount(500);
        requestDTO.setCurrencyCode("USD");
        requestDTO.setMerchantTimestamp(new Date());
        transactionService.initializeTransaction(requestDTO);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange("https://localhost:8444/api/initiate-payment-request", HttpMethod.POST,
                    new HttpEntity<PaymentRequestDTO>(requestDTO), String.class);
        } catch (Exception e) {
            System.out.println("Could not contact payment center");
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact payment center");
        }
        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", response.getBody());
        headersRedirect.add("Access-Control-Allow-Origin", "*");

        try {
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }


    }

    @GetMapping("auth/bankFail")
    public ResponseEntity<?> testBankFail() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        requestDTO.setMerchantEmail("test@gmail.com");
        requestDTO.setSuccessUrl("https://localhost:8447/view/success");
        requestDTO.setFailedUrl("https://localhost:8447/view/failed");
        requestDTO.setErrorUrl("https://localhost:8447/view/error");
        requestDTO.setAmount(10000);
        requestDTO.setCurrencyCode("USD");
        requestDTO.setMerchantTimestamp(new Date());
        transactionService.initializeTransaction(requestDTO);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange("https://localhost:8444/api/initiate-payment-request", HttpMethod.POST,
                    new HttpEntity<PaymentRequestDTO>(requestDTO), String.class);
        } catch (Exception e) {
            System.out.println("Could not contact payment center");
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact payment center");
        }
        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", response.getBody());
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        try {
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }
    }

    @GetMapping("auth/bankError")
    public ResponseEntity<?> testBankError() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        requestDTO.setMerchantEmail("test@gmail.com");
        requestDTO.setSuccessUrl("https://localhost:8447/view/success");
        requestDTO.setFailedUrl("https://localhost:8447/view/failed");
        requestDTO.setErrorUrl("https://localhost:8447/view/error");
        requestDTO.setAmount(100);
        requestDTO.setCurrencyCode("USD");
        requestDTO.setMerchantTimestamp(new Date());
        transactionService.initializeTransaction(requestDTO);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange("https://localhost:8444/api/initiate-payment-request", HttpMethod.POST,
                    new HttpEntity<PaymentRequestDTO>(requestDTO), String.class);
        } catch (Exception e) {
            System.out.println("Could not contact payment center");
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact payment center");
        }
        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", response.getBody());
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        try {
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }
    }

    @GetMapping("/paypal")
    public ResponseEntity<?> testPaypal() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        requestDTO.setMerchantEmail("sb-zx3ys4123984@business.example.com");
        requestDTO.setSuccessUrl("http://localhost:4200/success");
        requestDTO.setFailedUrl("http://localhost:4200/fail");
        requestDTO.setErrorUrl("http://localhost:4200/error");
        requestDTO.setCancelUrl("http://localhost:4200/cancel");
        requestDTO.setAmount(100);
        requestDTO.setCurrencyCode("USD");
        requestDTO.setMerchantTimestamp(new Date());
        transactionService.initializeTransaction(requestDTO);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange("https://localhost:8444/auth/api/initiate-payment-request", HttpMethod.POST,
                    new HttpEntity<PaymentRequestDTO>(requestDTO), String.class);
        } catch (Exception e) {
            System.out.println("Could not contact payment center");
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact payment center");
        }
        return ResponseEntity.ok(response.getBody());
    }


    @GetMapping("/paypal/subscribe")
    public ResponseEntity<?> testPaypalSubscribe(@AuthenticationPrincipal User user) {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        requestDTO.setMerchantEmail("sb-zx3ys4123984@business.example.com");
        requestDTO.setSuccessUrl("http://localhost:4200/success");
        requestDTO.setFailedUrl("http://localhost:4200/fail");
        requestDTO.setErrorUrl("http://localhost:4200/error");
        requestDTO.setCancelUrl("http://localhost:4200/cancel");
        requestDTO.setAmount(10);
        requestDTO.setCurrencyCode("USD");
        requestDTO.setMerchantTimestamp(new Date());
        transactionService.initializeTransaction(requestDTO);

        UserSubscription subscription = new UserSubscription();
        subscription.setOwner(user);
        subscription.setMerchantOrderId(requestDTO.getMerchantOrderId());
        subscription.setSubscriptionStatus(SubscriptionStatus.INITIATED);
        subscription.setPaymentAmount(10);
        subscription.setCurrency(requestDTO.getCurrencyCode());
        subscriptionRepository.save(subscription);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange("https://localhost:8444/auth/subscriptions/start", HttpMethod.POST,
                    new HttpEntity<PaymentRequestDTO>(requestDTO), String.class);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact payment center");
        }
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("auth/bitcoin")
    public ResponseEntity<?> testBitcoin() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        requestDTO.setMerchantEmail("sb-zx3ys4123984@business.example.com");
        requestDTO.setSuccessUrl("https://localhost:8447/view/success");
        requestDTO.setFailedUrl("https://localhost:8447/view/failed");
        requestDTO.setErrorUrl("https://localhost:8447/view/error");
        requestDTO.setCancelUrl("http://localhost:8447/view/dashboard");
        requestDTO.setAmount(100000);
        requestDTO.setCurrencyCode("USD");
        requestDTO.setMerchantTimestamp(new Date());
        transactionService.initializeTransaction(requestDTO);
        ResponseEntity<String> response = null;
        try {
        	System.out.println(requestDTO.getMerchantOrderId());
            response = restTemplate.exchange("https://localhost:8444/auth/api/initiate-payment-request", HttpMethod.POST,
                    new HttpEntity<PaymentRequestDTO>(requestDTO), String.class);
        } catch (Exception e) {
            System.out.println("Could not contact payment center");
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact payment center");
        }
        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", response.getBody());
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        try {
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }
    }
}
