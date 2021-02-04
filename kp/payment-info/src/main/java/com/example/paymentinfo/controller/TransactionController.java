package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.Currency;
import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.domain.TransactionStatus;
import com.example.paymentinfo.dto.TransactionDto;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.CurrencyService;
import com.example.paymentinfo.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth/transactions")
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
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


    /**
     * Update expired transactions every half hour
     */
    //@Scheduled(initialDelay = 10000, fixedRate = 1800000)
    @Scheduled(initialDelay = 10000, fixedRate = 300000)
    @Async
    public void cancelPendingTransactions() {
        List<Transaction> pendingTransactions = (List<Transaction>) transactionService.findAllByStatusIn(Arrays.asList(TransactionStatus.CREATED, TransactionStatus.NEW, TransactionStatus.PENDING));
        List<Transaction> expiredTransactions = new ArrayList<>();
        for (Transaction transaction : pendingTransactions) {
            if (addHours(transaction.getCreated(), 1).before(new Date())) {
                transaction.setStatus(TransactionStatus.EXPIRED);
                expiredTransactions.add(transaction);
                log.info("Expired transaction with id:" + transaction.getId());
            }
        }
        if (!expiredTransactions.isEmpty()) {
            transactionService.saveRange(expiredTransactions);
        }

    }

    Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
    
    @GetMapping("checkStatus/{merchantOrderId}")
    public ResponseEntity<String> checkStatus(@PathVariable long merchantOrderId){
    	
    	Transaction t = transactionService.findByMerchantOrderId(merchantOrderId);
    	System.out.println(merchantOrderId);
    	System.out.println(t.getStatus());
    	
    	if (t.getStatus() == null) {
    		log.error("Error while trying to check transaction status: transaction status not found.");
            return ResponseEntity.badRequest().build();
    	} else {
            log.info("TRANSACTION with PAYMENT ID: " + t.getPaymentID() + "   STATUS: " + t.getStatus());

    	}
        return ResponseEntity.ok(t.getStatus().toString());
    	
    }
}
