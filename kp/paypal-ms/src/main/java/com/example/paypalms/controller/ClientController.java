package com.example.paypalms.controller;

import com.example.paypalms.domain.Client;
import com.example.paypalms.dto.RegisterClientDTO;
import com.example.paypalms.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registrationDTO", new RegisterClientDTO());
        return "registration";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @ModelAttribute("registrationDTO") RegisterClientDTO registerClientDTO) {
        if (clientService.insert(new Client(registerClientDTO.getEmail(), registerClientDTO.getClientId(), registerClientDTO.getClientSecret())) != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
