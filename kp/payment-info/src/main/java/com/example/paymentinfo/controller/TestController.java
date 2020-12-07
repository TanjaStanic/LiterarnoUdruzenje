package com.example.paymentinfo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    private final RestTemplate restTemplate;

    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<String> testRouting(){
        return ResponseEntity.ok().body("I am payment-info-ms");
    }

    @GetMapping("test")
    public ResponseEntity<String> testBankMS(){
        System.out.println("Payment info");
        String fromBankMs = restTemplate.getForObject("https://bank-ms/test/luservice", String.class);
        fromBankMs+=", BankMS responded Payment info";
        return ResponseEntity.ok().body(fromBankMs);

    }
}
