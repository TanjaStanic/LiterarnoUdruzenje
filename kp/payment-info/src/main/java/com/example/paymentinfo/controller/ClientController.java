package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.dto.ClientRegistrationDto;
import com.example.paymentinfo.service.AuthService;
import com.example.paymentinfo.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class ClientController {

    private ClientService clientService;
    private RestTemplate restTemplate;
    private AuthService authService;

    public ClientController(ClientService clientService, RestTemplate restTemplate, AuthService authService) {
        this.clientService = clientService;
        this.restTemplate = restTemplate;
        this.authService = authService;
    }

    @PostMapping("auth/register")
    public String register(@Valid @ModelAttribute("client") ClientRegistrationDto clientRegistrationDto,
                           RedirectAttributes redirectAttrs, Model model) {
        System.out.println(clientRegistrationDto);
        Client client = null;
        try {
            client = authService.registerClient(clientRegistrationDto);
        } catch (Exception exception) {

            model.addAttribute("clientRegistrationDto", clientRegistrationDto);
            model.addAttribute("error",  exception.getMessage());
            return "registration";
        }
        return "redirect:/view/select-payment-methods/" + client.getId().toString();
    }


}
