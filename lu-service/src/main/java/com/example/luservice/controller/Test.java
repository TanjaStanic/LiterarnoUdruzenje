package com.example.luservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test {

	private final RestTemplate restTemplate;

	public Test(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


	@GetMapping("payment/info")
	public ResponseEntity<String> paymentInfo() {
		// get my payment methods
		String fromPaymentInfo = restTemplate.getForObject("/pc_info/", String.class);
		System.out.println("Literarno udruzenje");
		return ResponseEntity.ok().body(fromPaymentInfo);
	}

}
