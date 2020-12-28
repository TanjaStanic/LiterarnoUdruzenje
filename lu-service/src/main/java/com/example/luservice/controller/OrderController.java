package com.example.luservice.controller;

import com.example.luservice.dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<?> makeOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok().build();
    }
}
