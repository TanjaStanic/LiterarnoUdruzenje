package com.example.paypalms.controller;

import com.example.paypalms.domain.Subscription;
import com.example.paypalms.domain.SubscriptionPlan;
import com.example.paypalms.domain.Transaction;
import com.example.paypalms.dto.SubscriptionDto;
import com.example.paypalms.dto.SubscriptionPlanDto;
import com.example.paypalms.dto.SubscriptionRequestDto;
import com.example.paypalms.dto.TransactionDto;
import com.example.paypalms.enums.SubscriptionStatus;
import com.example.paypalms.service.PaymentService;
import com.example.paypalms.service.SubscriptionPlanService;
import com.example.paypalms.service.SubscriptionService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/subscriptions")
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class SubscriptionController {

    private SubscriptionPlanService subscriptionPlanService;
    private PaymentService paymentService;
    private SubscriptionService subscriptionService;
    private RestTemplate restTemplate;

    public SubscriptionController(SubscriptionPlanService subscriptionPlanService, PaymentService paymentService,
                                  SubscriptionService subscriptionService, RestTemplate restTemplate) {
        this.subscriptionPlanService = subscriptionPlanService;
        this.paymentService = paymentService;
        this.subscriptionService = subscriptionService;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<?> createBillingPlan(@RequestBody @Valid SubscriptionRequestDto subscriptionRequest) {
        //CREATE A BILLING PLAN
        Long subscriptionId;
        try {
            subscriptionId = paymentService.createBillingPlan(subscriptionRequest);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

        //CREATE A BILLING AGREEMENT
        String redirectUrl;
        try {
            redirectUrl = paymentService.createBillingAgreement(subscriptionRequest, subscriptionId);
        } catch (Exception e) {
            log.error("CANCELED | PayPal Subscription");
            return ResponseEntity.badRequest().build();
        }
        log.info("COMPLETED | PayPal Subscription");
        //redirect user to the paypal site
        return ResponseEntity.ok(redirectUrl);

    }


    @GetMapping("/complete")
    public ResponseEntity<?> completeSubscription(@RequestParam Long subscriptionId, @RequestParam String token) {
        Subscription subscription;
        try {
            subscription = subscriptionService.findById(subscriptionId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }

        String redirectUrl = null;
        try {
            redirectUrl = paymentService.executeBillingAgreement(subscription, token);
        } catch (PayPalRESTException exception) {
            exception.printStackTrace();
        }

        try {
            HttpEntity<SubscriptionDto> entity = new HttpEntity<>(new SubscriptionDto(subscription));
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(redirectUrl, entity, String.class);

            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", responseEntity.getBody());
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);


        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(exception);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> cancelBillingPlan(@RequestParam Long subscriptionId) {
        Subscription subscription = subscriptionService.findById(subscriptionId);
        subscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
        subscriptionService.save(subscription);
        try {
            HttpEntity<SubscriptionDto> entity = new HttpEntity<>(new SubscriptionDto(subscription));
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(subscription.getCancelUrl(), entity, String.class);

            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", responseEntity.getBody());
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(exception);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{sellerEmail}")
    public ResponseEntity<SubscriptionPlanDto[]> getSubscriptionPlansForSeller(@PathVariable String sellerEmail) {
        List<SubscriptionPlan> subscriptionPlans = (ArrayList) subscriptionPlanService.findAllBySellerEmail(sellerEmail);
        List<SubscriptionPlanDto> plans = subscriptionPlans.stream().map(SubscriptionPlanDto::new).collect(Collectors.toList());

        SubscriptionPlanDto[] retVal = plans.toArray(new SubscriptionPlanDto[0]);

        return ResponseEntity.ok(retVal);
    }
}
