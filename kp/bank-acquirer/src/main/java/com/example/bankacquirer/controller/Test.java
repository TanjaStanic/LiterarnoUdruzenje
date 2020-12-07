package com.example.bankacquirer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test {
	
	//premjestiti u config class kasnije
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public ResponseEntity<String> test() {
		System.out.println("Bank Acquirer");
		return ResponseEntity.ok("Bank Acquirer");
	}

	@GetMapping("/testBankAcquirer")
	public String testCommunication() {
		restTemplate = new RestTemplate();
        String fromBankAq = restTemplate.getForObject("http://localhost:8441/testBankMS", String.class);
		System.out.println(fromBankAq);
		fromBankAq+=", Bank Acquirer Test-done";
		return fromBankAq;
	}
	
}
