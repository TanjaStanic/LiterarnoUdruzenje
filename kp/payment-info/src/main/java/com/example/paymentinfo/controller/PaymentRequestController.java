package com.example.paymentinfo.controller;


import com.example.paymentinfo.domain.PaymentRequest;
import com.example.paymentinfo.repository.PaymentRequestRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.paymentinfo.dto.PaymentRequestDTO;
import com.example.paymentinfo.service.PaymentRequestService;
import com.example.paymentinfo.service.TransactionService;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/auth/api")
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class PaymentRequestController {

    private PaymentRequestService paymentService;
    private TransactionService transactionService;
    private PaymentRequestRepository paymentRequestRepository;
    private RestTemplate restTemplate;

    public PaymentRequestController(PaymentRequestService paymentService, TransactionService transactionService,
                                    PaymentRequestRepository paymentRequestRepository, RestTemplate restTemplate) {
        this.paymentService = paymentService;
        this.transactionService = transactionService;
        this.paymentRequestRepository = paymentRequestRepository;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/initiate-payment-request")
    public ResponseEntity<String> createRequest(@RequestBody PaymentRequestDTO requestDTO) {

        PaymentRequest request = new PaymentRequest();
        request.setMerchantOrderId(requestDTO.getMerchantOrderId());
        request.setMerchantEmail(requestDTO.getMerchantEmail());
        request.setSuccessUrl(requestDTO.getSuccessUrl());
        request.setFailedUrl(requestDTO.getFailedUrl());
        request.setErrorUrl(requestDTO.getErrorUrl());
        request.setAmount(requestDTO.getAmount());
        request.setCurrencyCode(requestDTO.getCurrencyCode());
        request.setMerchantTimestamp(requestDTO.getMerchantTimestamp());


        request = paymentRequestRepository.save(request);
        log.info("CREATED | Payment Requests | Payment Id: " + request.getId());
        
        transactionService.initializeTransaction(requestDTO);
        return ResponseEntity.ok("https://localhost:8444/view/payment-methods/" + requestDTO.getMerchantEmail() + "/" + requestDTO.getMerchantOrderId());

    }

    @GetMapping("/{paymentMethod}/{sellerEmail}/{merchantOrderId}")
    public ResponseEntity<?> redirectPaymentRequest(@PathVariable String paymentMethod, @PathVariable String sellerEmail, @PathVariable long merchantOrderId) {

        PaymentRequest paymentRequest = paymentRequestRepository.findByMerchantOrderId(merchantOrderId);
        System.out.println(paymentRequest.getMerchantEmail().toString());
        System.out.println(paymentMethod.toString());
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest);
        System.out.println(entity);
        ResponseEntity<String> redirectUrl = restTemplate.postForEntity("https://" + paymentMethod + "/", entity, String.class);
        System.out.println(paymentMethod.toString());
        System.out.println(entity.toString());
        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", redirectUrl.getBody());
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

    }



}