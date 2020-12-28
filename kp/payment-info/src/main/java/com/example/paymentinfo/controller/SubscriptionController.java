package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.PaymentRequest;
import com.example.paymentinfo.dto.BillingPlanDto;
import com.example.paymentinfo.dto.SubscriptionRequestDto;
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
public class SubscriptionController {

    private RestTemplate restTemplate;

    public SubscriptionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<?> redirect(@ModelAttribute BillingPlanDto billingPlanDto) {

        HttpEntity<SubscriptionRequestDto> entity = new HttpEntity<>(new SubscriptionRequestDto(billingPlanDto.getSubscriptionPlan(), billingPlanDto.getSellerEmail()));
        // redirectUrl je paypal api url
        ResponseEntity<String> redirectUrl = restTemplate.postForEntity(MessageFormat.format("https://{0}/subscriptions", billingPlanDto.getPaymentMethod()), entity, String.class);

        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", redirectUrl.getBody());
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

    }
}
