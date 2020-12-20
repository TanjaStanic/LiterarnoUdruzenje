package com.example.luservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("view")
public class ViewController {

    @GetMapping("/success")
    public String getSuccessPage(Model model) {
        return "successPage";
    }

    @GetMapping("/failed")
    public String getFailedPage(Model model) {
        return "failedPage";
    }

    @GetMapping("/error")
    public String getErrorPage(Model model) {
        return "errorPage";
    }
    
    @GetMapping("/payment-methods")
    public String getPaymentMethods(Model model) {
        return "paymentMethods";
    }
    
    @GetMapping("/order")
    public String getOrder(Model model) {
        return "order";
    }

}
