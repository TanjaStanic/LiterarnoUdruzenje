package com.example.luservice.controller;

import com.example.luservice.dto.NewUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/order")
    public String getOrder(Model model) {
        return "order";
    }

    @GetMapping("/dashborad")
    public String getDashborad(Model model){
        return "dashboard";
    }

}
