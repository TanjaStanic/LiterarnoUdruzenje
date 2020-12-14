package com.example.bankacquirer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankacquirer.dto.PaymentConcentratorRequestDTO;
import com.example.bankacquirer.dto.PaymentConcentratorResponseDTO;
import com.example.bankacquirer.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	
	private PaymentService paymentService;
	
	@RequestMapping(value = "/create-response", method = RequestMethod.POST)
	private ResponseEntity<?> createResponse(@RequestBody PaymentConcentratorRequestDTO paymentConcentratorRequestDTO) {
		
		PaymentConcentratorResponseDTO pcResponseDTO = paymentService.createResponse(paymentConcentratorRequestDTO);
		
		return new ResponseEntity<>(pcResponseDTO, HttpStatus.OK);
	}
	

}
