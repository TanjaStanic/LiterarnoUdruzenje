package com.example.luservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

	@GetMapping("/testLuService")
	public String testCommunication() {
		
		return "Lu-service  test-done";
	}
}
