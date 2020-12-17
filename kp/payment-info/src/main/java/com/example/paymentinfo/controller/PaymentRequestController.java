package com.example.paymentinfo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.paymentinfo.dto.PaymentRequestDTO;
import com.example.paymentinfo.dto.PaymentResponseDTO;
import com.example.paymentinfo.service.PaymentRequestService;

@Controller
@RequestMapping("/payment")
public class PaymentRequestController {
	
private PaymentRequestService paymentService;
	

}
