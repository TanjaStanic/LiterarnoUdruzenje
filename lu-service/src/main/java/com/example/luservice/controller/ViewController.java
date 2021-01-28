package com.example.luservice.controller;

import com.example.luservice.dto.PaymentResponseDto;
import com.example.luservice.model.Transaction;
import com.example.luservice.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("view")
public class ViewController {

    private TransactionService transactionService;

    public ViewController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/success")
    public ResponseEntity<String> getSuccessPage(@RequestBody PaymentResponseDto paymentResponse) {
        Transaction transaction = transactionService.findByMerchantOrderId(paymentResponse.getMerchantOrderId());
        if (transaction != null) {
            transaction.setStatus(paymentResponse.getStatus());
            transaction.setPaymentID(paymentResponse.getPaymentID());
            transactionService.save(transaction);
            return ResponseEntity.ok("http://localhost:4200/success");
        }
        return ResponseEntity.ok("http://localhost:4200/error");

    }

    @PostMapping("/failed")
    public ResponseEntity<String> getFailedPage(@RequestBody PaymentResponseDto paymentResponse) {
        Transaction transaction = transactionService.findByMerchantOrderId(paymentResponse.getMerchantOrderId());
        if (transaction != null) {
            transaction.setStatus(paymentResponse.getStatus());
            transaction.setPaymentID(paymentResponse.getPaymentID());
            transactionService.save(transaction);
            return ResponseEntity.ok("http://localhost:4200/fail");
        }
        return ResponseEntity.ok("http://localhost:4200/error");
    }

    @PostMapping("/error")
    public ResponseEntity<String> getErrorPage(@RequestBody PaymentResponseDto paymentResponse) {

        Transaction transaction = transactionService.findByMerchantOrderId(paymentResponse.getMerchantOrderId());
        if (transaction != null) {
            transaction.setStatus(paymentResponse.getStatus());
            transaction.setPaymentID(paymentResponse.getPaymentID());
            transactionService.save(transaction);
            return ResponseEntity.ok("http://localhost:4200/error");
        }
        return ResponseEntity.ok("http://localhost:4200/error");
    }


    @PostMapping("/cancel")
    public ResponseEntity<String> getCancel(@RequestBody PaymentResponseDto paymentResponse) {

        Transaction transaction = transactionService.findByMerchantOrderId(paymentResponse.getMerchantOrderId());
        if (transaction != null) {
            transaction.setStatus(paymentResponse.getStatus());
            transaction.setPaymentID(paymentResponse.getPaymentID());
            transactionService.save(transaction);
            return ResponseEntity.ok("http://localhost:4200/cancel");
        }
        return ResponseEntity.ok("http://localhost:4200/error");
    }

    @GetMapping("/order")
    public String getOrder(Model model) {
        return "order";
    }


}
