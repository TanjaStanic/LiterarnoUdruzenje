package com.example.luservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.service.PaymentRequestService;
import com.example.luservice.service.PaymentService;
import com.example.luservice.service.TransactionService;

@Controller
public class PaymentController {

	@Autowired
	private PaymentService paymentService;	
	@Autowired
	private TransactionService transactionService;
	
	
	@PostMapping(value = "/confirm-order")
    public ResponseEntity<?> createPaymentRequest(String clientID, double amount)  {
		
		//kada kupac kliknee na kupi
		//treba da mu se otvori stranica sa pay info gdje birra metod placanja
		//pay info salje podatke o prodavcu i iznosu,
		PaymentRequestDTO pReq = paymentService.createPaymentRequest(clientID, amount);
		
		//cuva transakciju
		transactionService.initializeTransaction(pReq);
				
		return new ResponseEntity<>(pReq, HttpStatus.OK);
    }
}
