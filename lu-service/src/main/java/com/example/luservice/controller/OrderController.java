package com.example.luservice.controller;

import com.example.luservice.dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/orders")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", maxAge = 3600)
public class OrderController {

    @PostMapping
    public ResponseEntity<?> makeOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok().build();
    }
}
