package com.example.bankacquirer.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
	
	@RequestMapping(value = "/card-data/{paymentRequestId}") 
	public String form(@PathVariable Long paymentRequestId, Model model) {
		model.addAttribute("paymentRequestId", paymentRequestId);
		return "form";
	}
}
