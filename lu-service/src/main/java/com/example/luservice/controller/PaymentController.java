package com.example.luservice.controller;

import com.example.luservice.model.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.service.PaymentRequestService;
import com.example.luservice.service.PaymentService;
import com.example.luservice.service.TransactionService;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RestTemplate restTemplate;


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
        requestDTO.setMerchantOrderId((long)Math.floor(Math.random()*9_000_000_000L)+1_000_000_000L);
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
        }
        catch (Exception e) {   	
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }
        

    }

    @GetMapping("auth/bankFail")
    public ResponseEntity<?> testBankFail() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long)Math.floor(Math.random()*9_000_000_000L)+1_000_000_000L);
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
        }
        catch (Exception e) {   	
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }
    }

    @GetMapping("auth/bankError")
    public ResponseEntity<?> testBankError() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long)Math.floor(Math.random()*9_000_000_000L)+1_000_000_000L);
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
        }
        catch (Exception e) {   	
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }
    }

    @GetMapping("auth/paypal")
    public ResponseEntity<?> testPaypal() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long)Math.floor(Math.random()*9_000_000_000L)+1_000_000_000L);
        requestDTO.setMerchantEmail("sb-zx3ys4123984@business.example.com");
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
        }
        catch (Exception e) {   	
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }
    }

    @GetMapping("auth/paypal/subscribe")
    public ResponseEntity<?> testPaypalSubscribe() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long)Math.floor(Math.random()*9_000_000_000L)+1_000_000_000L);
        requestDTO.setMerchantEmail("sb-zx3ys4123984@business.example.com");
        requestDTO.setSuccessUrl("https://localhost:8447/view/success");
        requestDTO.setFailedUrl("https://localhost:8447/view/failed");
        requestDTO.setErrorUrl("https://localhost:8447/view/error");
        requestDTO.setCancelUrl("https://localhost:8447/view/dashborad");
        requestDTO.setAmount(10);
        requestDTO.setCurrencyCode("USD");
        requestDTO.setMerchantTimestamp(new Date());
        transactionService.initializeTransaction(requestDTO);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange("https://localhost:8444/subscriptions/start", HttpMethod.POST,
                    new HttpEntity<PaymentRequestDTO>(requestDTO), String.class);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact payment center");
        }
        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", response.getBody());
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        try {
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
        }
        catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }
    }
    
    @GetMapping("bitcoin")
    public ResponseEntity<?> testBitcoin() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long)Math.floor(Math.random()*9_000_000_000L)+1_000_000_000L);
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
        }
        catch (Exception e) {   	
            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact" + response.getBody());
        }
    }
}
