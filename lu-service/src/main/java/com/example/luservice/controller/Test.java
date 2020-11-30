package com.example.luservice.controller;

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
	
	@GetMapping("/testLuService")
	public String testCommunication() {
		
		return "Lu-service  test-done";
	}
	@GetMapping("/finallTest")
	public ResponseEntity<String> finallTest(){
	        
	        restTemplate = new RestTemplate();
	        String finall = restTemplate.getForObject("http://localhost:8445/testBankAcquirer", String.class);
			System.out.println(finall);
			finall+=", all tests are done";
	        return ResponseEntity.ok().body(finall);
	}
}
