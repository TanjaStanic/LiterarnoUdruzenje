package com.example.bankpcc.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.bankpcc.domain.Transaction;
import com.example.bankpcc.dto.PccRequestDTO;
import com.example.bankpcc.dto.PccResponseDTO;
import com.example.bankpcc.service.PaymentService;
import com.example.bankpcc.service.TransactionService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value = "/redirect-request", method = RequestMethod.POST)
	private ResponseEntity<?> redirectRequest(@RequestBody PccRequestDTO pccRequestDTO) {
		if (!paymentService.checkRequest(pccRequestDTO)) {
			return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	
		//paymentService.saveRequest(pccRequestDTO);
		Transaction transaction = transactionService.createTransaction(pccRequestDTO);
		
		PccResponseDTO response = paymentService.sendResponse(pccRequestDTO);
		transaction = transactionService.updateTransaction(response, transaction.getId());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
