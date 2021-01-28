package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.dto.ClientBasicInfoDto;
import com.example.paymentinfo.dto.ClientDto;
import com.example.paymentinfo.service.AuthService;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.utils.ClientValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.stream.Collectors;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class ClientController {

    private ClientService clientService;
    private RestTemplate restTemplate;
    private AuthService authService;


    public ClientController(ClientService clientService, RestTemplate restTemplate, AuthService authService) {
        this.clientService = clientService;
        this.restTemplate = restTemplate;
        this.authService = authService;

    }

    @GetMapping("auth/clients/{clientId}")
    public ResponseEntity<?> getClientInfo(@PathVariable Long clientId) {
        Client client;
        try {
            client = clientService.findById(clientId);
            ClientBasicInfoDto retVal = new ClientBasicInfoDto(client.getName(), client.getEmail());
            return ResponseEntity.ok(retVal);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

    }

    @GetMapping("clients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientDto>> getClients() {
        List<ClientDto> clients = clientService.getAll().stream().map(client -> new ClientDto(client)).collect(Collectors.toList());
        return ResponseEntity.ok(clients);
    }

}
