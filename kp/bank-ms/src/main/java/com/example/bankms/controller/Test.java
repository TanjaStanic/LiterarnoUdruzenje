package com.example.bankms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test {
	
	@Autowired
	private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<String> testRouting(){
        return ResponseEntity.ok().body("I am bank-ms");
    }

    @GetMapping("/test")
    public ResponseEntity<String> testBankAcquirer(){
        String fromBankAcq = restTemplate.getForObject("https://localhost:8445", String.class);
        System.out.println("Bank MS");
        fromBankAcq+=", BankAQ responded";
        return ResponseEntity.ok().body(fromBankAcq);
    }

    @GetMapping("/test/luservice")
    public ResponseEntity<String> test(){
        String response = restTemplate.getForObject("https://localhost:8447/testLuService", String.class);
        return ResponseEntity.ok( response + "  I am bank ms");
    }


}
