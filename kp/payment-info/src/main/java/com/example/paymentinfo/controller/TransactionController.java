package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.Currency;
import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.dto.TransactionDto;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.CurrencyService;
import com.example.paymentinfo.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
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
            return ResponseEntity.badRequest().build();
        }
        Transaction t = transactionService.findByPaymentID(transaction.getPaymentID());
        if (t != null) {
            t = new Transaction(seller, transaction.getStatus(), transaction.getPaymentID(), transaction.getAmount(), currency);
        } else {
            t.setPaymentID(transaction.getPaymentID());
            t.setStatus(transaction.getStatus());
            t.setAmount(transaction.getAmount());
        }

        transactionService.save(t);

        return ResponseEntity.ok().build();
    }
}
