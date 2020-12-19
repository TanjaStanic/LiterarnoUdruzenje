package com.example.luservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.model.PaymentRequest;
import com.example.luservice.service.PaymentRequestService;

@Controller
@RequestMapping(value = "/api/paymentController")
public class PaymentRequestController {
	
	  @Autowired
	  private RestTemplate restTemplate;
	  
	  @Autowired
	  private PaymentRequestService paymentRequestService;

	    /*@PostMapping(value = "/payment")
	    public ResponseEntity<PaymentRequestDTO> createPaymentRequest(@RequestBody @Valid PaymentRequestDTO requestDTO)  {

	        PaymentRequest paymentRequest = paymentRequestService.createPaymentRequest(requestDTO.getClient(), requestDTO.getAmount());
	        PaymentRequestDTO paymentDataDTO =restTemplate.postForObject("https://localhost:8444/api/get-payment-url",
	                paymentRequest, PaymentRequestDTO.class);
	        
	        return ResponseEntity.ok(paymentDataDTO);
	    }*/
	    
	    @GetMapping("/payment")
	    public ResponseEntity<String> testBankAcquirer(){
	        String from = restTemplate.getForObject("https://localhost:8441", String.class);
	        System.out.println("LU MS");
	        from+=", PaymentInfo responded";
	        return ResponseEntity.ok().body(from);
	    }

}
