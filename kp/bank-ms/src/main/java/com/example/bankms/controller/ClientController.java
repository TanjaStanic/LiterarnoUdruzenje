package com.example.bankms.controller;

import com.example.bankms.domain.Client;
import com.example.bankms.dto.ClientCredentials;
import com.example.bankms.dto.RegisterClientDTO;
import com.example.bankms.service.ClientService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.text.MessageFormat;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private RestTemplate restTemplate;

    public ClientController(ClientService clientService, RestTemplate restTemplate) {
        this.clientService = clientService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/register-url/{clientId}")
    public @ResponseBody
    ResponseEntity<String> getRegistrationUrl(@PathVariable String clientId) {
        return ResponseEntity.ok(MessageFormat.format("https://localhost:8441/clients/register/{0}", clientId));
    }

    @GetMapping("/register/{clientId}")
    public ResponseEntity<?> register(Model model, @PathVariable String clientId) {
        Client client = new Client();
        ResponseEntity<RegisterClientDTO> clientInfo = restTemplate.getForEntity(
                "https://localhost:8762/api/pc_info/auth/clients/" + clientId,
                RegisterClientDTO.class);

        if (clientInfo.getStatusCode() == HttpStatus.OK){
            client.setEmail(clientInfo.getBody().getEmail());
            client.setName(clientInfo.getBody().getName());
        }else {
            return ResponseEntity.badRequest().body(clientInfo.getBody());
        }

        client.setPcClientId(Long.parseLong(clientId));
        client = clientService.insert(client);

        HttpEntity<RegisterClientDTO> entity = new HttpEntity<>(clientInfo.getBody());
        ResponseEntity<ClientCredentials> response = restTemplate.postForEntity(
                "https://localhost:8445/clients", entity, ClientCredentials.class);

        ClientCredentials clientCredentials = response.getBody();
        client.setMerchantID(clientCredentials.getMerchantID());
        client.setMerchantPassword(clientCredentials.getMerchantPassword());
        client = clientService.update(client);

        String redirectUrl;
        if (response.getStatusCode() == HttpStatus.OK) {
            redirectUrl = MessageFormat.format("https://localhost:8762/api/pc_info/view/select-payment-methods/{0}", clientId);
            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", redirectUrl);
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
        } else {
            return ResponseEntity.badRequest().body(response.getBody());
        }


    }

}
