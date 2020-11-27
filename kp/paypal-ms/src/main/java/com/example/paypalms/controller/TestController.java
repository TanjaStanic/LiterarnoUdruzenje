package com.example.paypalms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping
    public ResponseEntity<String> testRouting(){
        return ResponseEntity.ok().body("I am paypal-ms");
    }
}
