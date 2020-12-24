package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.Currency;
import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.dto.TransactionDto;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.CurrencyService;
import com.example.paymentinfo.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@Log4j2
public class TransactionController {

    private TransactionService transactionService;
    private ClientService clientService;
    private CurrencyService currencyService;

    public TransactionController(TransactionService transactionService, ClientService clientService, CurrencyService currencyService) {
        this.transactionService = transactionService;
        this.clientService = clientService;
        this.currencyService = currencyService;
    }

    @PostMapping
    public ResponseEntity<?> updateTransaction(@RequestBody TransactionDto transaction) {
        Client seller = clientService.findByEmail(transaction.getSellerEmail());
        Currency currency = currencyService.findByCode(transaction.getCurrencyCode());

        if (seller == null) {
            log.error("Error while trying to update transaction: seller not found.");
            return ResponseEntity.badRequest().build();
        }
        Transaction t = transactionService.findByMerchantOrderId(transaction.getMerchantOrderId());

        if (t == null) {
            log.error("Error while trying to update transaction: transaction not found");
            return ResponseEntity.badRequest().build();
        } else {
            t.setPaymentID(transaction.getPaymentID());
            t.setStatus(transaction.getStatus());
            t.setAmount(transaction.getAmount());
            if (currency != null) {
                t.setCurrency(currency);
            }
        }

        transactionService.save(t);
        log.info("TRANSACTION with PAYMENT ID: " + transaction.getPaymentID() + "   STATUS UPDATED: " + transaction.getStatus());
        return ResponseEntity.ok().build();
    }
}
