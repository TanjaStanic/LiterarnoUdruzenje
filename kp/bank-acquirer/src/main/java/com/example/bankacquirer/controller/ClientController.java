package com.example.bankacquirer.controller;

import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.dto.ClientDto;
import com.example.bankacquirer.dto.NewClientDto;
import com.example.bankacquirer.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> registerNewClient(@RequestBody NewClientDto newClientDto) {
        Client client = null;
        try {
            client = clientService.insert(newClientDto);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body("Email already in use.");
        }
        client = clientService.saveClient(client);
        ClientDto clientDto = new ClientDto(client.getMerchantID(), client.getMerchantPassword(), client.getEmail(), client.getName());
        return ResponseEntity.ok(clientDto);
    }
}
