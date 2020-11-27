package com.example.bankms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test {
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Autowired
	private RestTemplate restTemplate;

    @GetMapping("/helloBank")
    public ResponseEntity<String> test(){
        System.out.println("Pogodio banku");
        return ResponseEntity.ok().body("Hello from bank mc!");
    }
    @GetMapping("/testBankMS")
    public ResponseEntity<String> testComunication(){
        
        restTemplate = new RestTemplate();
        String fromGateway = restTemplate.getForObject("http://localhost:8762/testGateway", String.class);
		System.out.println(fromGateway);
		fromGateway+=", BankMS Test-done";
        return ResponseEntity.ok().body(fromGateway);
    }

}
