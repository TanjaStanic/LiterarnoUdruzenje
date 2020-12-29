package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.Currency;
import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.domain.TransactionStatus;
import com.example.paymentinfo.dto.BillingPlanDto;
import com.example.paymentinfo.dto.PaymentRequestDTO;
import com.example.paymentinfo.dto.SubscriptionRequestDto;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.CurrencyService;
import com.example.paymentinfo.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;


@RestController
@RequestMapping("/subscriptions")
@Log4j2
public class SubscriptionController {

    private RestTemplate restTemplate;
    private ClientService clientService;
    private TransactionService transactionService;
    private CurrencyService currencyService;

    public SubscriptionController(RestTemplate restTemplate, ClientService clientService, TransactionService transactionService, CurrencyService currencyService) {
        this.restTemplate = restTemplate;
        this.clientService = clientService;
        this.transactionService = transactionService;
        this.currencyService = currencyService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> initiateSubscription( @RequestBody PaymentRequestDTO paymentRequest){
        Client client = clientService.findByEmail(paymentRequest.getMerchantEmail());
        Currency currency = currencyService.findByCode(paymentRequest.getCurrencyCode());

        Transaction transaction = new Transaction(client, TransactionStatus.CREATED, paymentRequest.getMerchantOrderId(), paymentRequest.getAmount(), currency);
        transaction.setSuccessUrl(paymentRequest.getSuccessUrl());
        transaction.setFailedUrl(paymentRequest.getFailedUrl());
        transaction.setErrorUrl(paymentRequest.getErrorUrl());
        transaction.setCancelUrl(paymentRequest.getCancelUrl());
        transaction = transactionService.save(transaction);
        log.info("SUBSCRIPTIONS | Transaction created");
        return ResponseEntity.ok(MessageFormat.format("https://localhost:8444/view/subscriptions/payment-methods/{0}/{1}",paymentRequest.getMerchantEmail(), transaction.getId().toString()));
    }

    @PostMapping
    public ResponseEntity<?> redirect(@ModelAttribute BillingPlanDto billingPlanDto) {

        Transaction transaction;
        try {
            transaction =transactionService.findById(Long.parseLong(billingPlanDto.getTransactionId()));
        }catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        SubscriptionRequestDto subscriptionRequest = new SubscriptionRequestDto(billingPlanDto.getSubscriptionPlan(), transaction.getSeller().getEmail(),
                                                        transaction.getAmount(), transaction.getCurrency().getCode(), transaction.getSuccessUrl(),
                                                        transaction.getFailedUrl(), transaction.getErrorUrl(), transaction.getCancelUrl());
        HttpEntity<SubscriptionRequestDto> entity = new HttpEntity<>(subscriptionRequest);
        // redirectUrl je paypal api url
        ResponseEntity<String> redirectUrl = restTemplate.postForEntity(MessageFormat.format("https://{0}/subscriptions", billingPlanDto.getPaymentMethod()), entity, String.class);

        //return ResponseEntity.ok(redirectUrl);
        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", redirectUrl.getBody());
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

    }

}
