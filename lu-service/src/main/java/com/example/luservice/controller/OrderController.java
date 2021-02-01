package com.example.luservice.controller;

import com.example.luservice.dto.OrderDTO;
import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.model.User;
import com.example.luservice.service.TransactionService;
import com.example.luservice.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/orders")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", maxAge = 3600)
public class OrderController {
    private TransactionService transactionService;
    private RestTemplate restTemplate;
    private UserService userService;

    public OrderController(TransactionService transactionService, RestTemplate restTemplate, UserService userService) {
        this.transactionService = transactionService;
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> makeOrder(@RequestBody OrderDTO orderDTO, @AuthenticationPrincipal User user) {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setMerchantOrderId((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        requestDTO.setMerchantEmail("sb-zx3ys4123984@business.example.com");
        requestDTO.setSuccessUrl("https://literary.association:8447/view/success");
        requestDTO.setFailedUrl("https://literary.association:8447/view/failed");
        requestDTO.setErrorUrl("https://literary.association:8447/view/error");
        requestDTO.setCancelUrl("https://literary.association:8447/view/cancel");
        requestDTO.setAmount(500);
        requestDTO.setCurrencyCode("USD");
        requestDTO.setMerchantTimestamp(new Date());
        transactionService.initializeTransaction(requestDTO);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange("https://payment.center:8444/auth/api/initiate-payment-request", HttpMethod.POST,
                    new HttpEntity<PaymentRequestDTO>(requestDTO), String.class);

            if (user != null){
                user.getBooks().addAll(orderDTO.getOrderItems().stream().map(orderItemDto -> orderItemDto.getBook()).collect(Collectors.toList()));
                userService.update(user);
            }
        } catch (Exception e) {
            e.printStackTrace();

            return (ResponseEntity<?>) ResponseEntity.badRequest().body("Could not contact payment center");
        }
        return ResponseEntity.ok(response.getBody());
    }
}
