package com.example.bankms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test {
	
	//@Autowired
   // private RestTemplate restTemplate;

    @GetMapping("/helloBank")
    public ResponseEntity<String> test(){
        System.out.println("Pogodio banku");
        return ResponseEntity.ok().body("Hello from bank!");
    }

    @GetMapping
    public ResponseEntity<String> testRouting(){
        return ResponseEntity.ok().body("I am bank-ms");
    }

}
