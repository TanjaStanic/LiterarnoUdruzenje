package com.example.luservice.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

}
