package com.example.paymentinfo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.paymentinfo.dto.PaymentRequestDTO;
import com.example.paymentinfo.dto.PaymentResponseDTO;
import com.example.paymentinfo.service.PaymentRequestService;
import com.example.paymentinfo.service.TransactionService;

@Controller
@RequestMapping("/api")
public class PaymentRequestController {

	private PaymentRequestService paymentService;
	private TransactionService transactionService;
	
	public PaymentRequestController(PaymentRequestService paymentService) {
		this.paymentService = paymentService;
	}
	
	
	@PostMapping("/initiate-payment-request")
	public String createRequest(PaymentRequestDTO pReqDTO){
		
		transactionService.initializeTransaction(pReqDTO);
		
		return "https://localhost:8444/view/payment-methods";
	}
	
	

	@GetMapping("get-payment-url")
    public ResponseEntity<String> testRouting() {
		System.out.println("ULAZIIIIIIIIIIII");
        return ResponseEntity.ok().body("I am payment-info-ms");
    }

	/*@PostMapping("/get-payment-url")
	public ResponseEntity<String> getPaymentUrl(@RequestBody PaymentRequestDTO request) {

    System.out.println("ULAZIIIIIIIIIIII");
    PaymentRequestDTO paymentData = paymentService.getPaymentUrl(request);
    return ResponseEntity.ok(paymentData.getPaymentUrl());
}*/
	

}
