package com.example.bankpcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.bankpcc.domain.PccRequest;
import com.example.bankpcc.dto.PccRequestDTO;
import com.example.bankpcc.service.PaymentService;

@Controller
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(value = "/redirect-request", method = RequestMethod.POST)
	private ResponseEntity<?> redirectRequest(@RequestBody PccRequestDTO pccRequestDTO) {
		if (!paymentService.checkRequest(pccRequestDTO)) {
			return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		PccRequest pccRequest = paymentService.saveRequest(pccRequestDTO);
		return new ResponseEntity<>(pccRequestDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/create-response",method=RequestMethod.GET)
	private ResponseEntity<?> createResponse(){
		
		return new ResponseEntity<String>("Test PCC works", HttpStatus.OK);
	}
}
