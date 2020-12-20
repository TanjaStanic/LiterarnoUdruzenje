package com.example.paymentinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("view")
public class ViewController {
	
	 @GetMapping("/payment-methods")
	    public String getPaymentMethods(Model model) {
	        return "paymentMethods";
	 }


}
