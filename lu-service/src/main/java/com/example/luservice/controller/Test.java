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

	@GetMapping
	public ResponseEntity<String> test() {
		String fromBankMs = restTemplate.getForObject("/banking", String.class);
		System.out.println("Literarno udruzenje");
		fromBankMs+=", BankMS responded Test completed";
		return ResponseEntity.ok().body(fromBankMs);

	}

	@GetMapping("payment/info")
	public ResponseEntity<String> paymentInfo() {
		String fromPaymentInfo = restTemplate.getForObject("/pc_info/test", String.class);
		System.out.println("Literarno udruzenje");
		fromPaymentInfo+=", Payment Info responded Test completed";
		return ResponseEntity.ok().body(fromPaymentInfo);

	}
	@GetMapping("/testLuService")
	public String testCommunication() {
		
		return "Lu-service  test-done";
	}
}
