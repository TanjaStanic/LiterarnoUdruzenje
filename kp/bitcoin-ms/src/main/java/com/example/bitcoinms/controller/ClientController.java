package com.example.bitcoinms.controller;

import com.example.bitcoinms.domain.Client;
import com.example.bitcoinms.dto.ClientInfoDto;
import com.example.bitcoinms.dto.RegisterClientDTO;
import com.example.bitcoinms.service.ClientService;
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
@RequestMapping("auth/clients")
public class ClientController {

    private ClientService clientService;
    private RestTemplate restTemplate;

    public ClientController(ClientService clientService, RestTemplate restTemplate) {
        this.clientService = clientService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/register-url/{clientId}")
    public @ResponseBody
    ResponseEntity<String> getRegistrationUrl(@PathVariable String clientId) {
        return ResponseEntity.ok(MessageFormat.format("https://localhost:8442/auth/clients/register/{0}", clientId));
    }

    @GetMapping("/register/{clientId}")
    public Object registerForm(Model model, @PathVariable String clientId) {
        RegisterClientDTO registerClientDTO = new RegisterClientDTO();
        ResponseEntity<ClientInfoDto> clientInfo = restTemplate.getForEntity(
                "https://localhost:8762/api/pc_info/auth/clients/" + clientId,
                ClientInfoDto.class);

        if (clientInfo.getStatusCode() == HttpStatus.OK) {
            registerClientDTO.setEmail(clientInfo.getBody().getEmail());
            registerClientDTO.setName(clientInfo.getBody().getName());
        } else {
            return ResponseEntity.badRequest().body(clientInfo.getBody());
        }

        registerClientDTO.setPcClientId(clientId);
        model.addAttribute("registrationDTO", registerClientDTO);
        return "registration";
    }

    @PostMapping("/register")
    public Object register(@Valid @ModelAttribute("registrationDTO") RegisterClientDTO registerClientDTO, Model model) {
        Client newClient = new Client();
        newClient.setEmail(registerClientDTO.getEmail());
        newClient.setName(registerClientDTO.getName());
        newClient.setPcClientId(Long.parseLong(registerClientDTO.getPcClientId()));
        newClient.setToken(registerClientDTO.getToken());

        Client client = clientService.findByEmail(newClient.getEmail());

        if (client != null) {
            model.addAttribute("error", "Email already in use.");
            return "registration";
        }
        if (clientService.insert(newClient) != null) {
            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", MessageFormat.format("https://localhost:8762/api/pc_info/view/select-payment-methods/{0}", registerClientDTO.getPcClientId()));
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

        } else {
            model.addAttribute("registrationDTO", registerClientDTO);
            model.addAttribute("error", "Something went wrong, please try again.");
            return "registration";
        }

    }


    @GetMapping("/support/{clientId}")
    public Object supportForm(Model model, @PathVariable String clientId) {
        RegisterClientDTO registerClientDTO = new RegisterClientDTO();
        ResponseEntity<ClientInfoDto> clientInfo = restTemplate.getForEntity(
                "https://localhost:8762/api/pc_info/auth/clients/" + clientId,
                ClientInfoDto.class);

        if (clientInfo.getStatusCode() == HttpStatus.OK) {
            registerClientDTO.setEmail(clientInfo.getBody().getEmail());
            registerClientDTO.setName(clientInfo.getBody().getName());
        } else {
            return ResponseEntity.badRequest().body(clientInfo.getBody());
        }

        registerClientDTO.setPcClientId(clientId);
        model.addAttribute("registrationDTO", registerClientDTO);
        return "support";
    }

    @PostMapping("/support")
    public Object supportPaymentMethod(@Valid @ModelAttribute("registrationDTO") RegisterClientDTO registerClientDTO, Model model) {
        Client newClient = new Client();
        newClient.setEmail(registerClientDTO.getEmail());
        newClient.setName(registerClientDTO.getName());
        newClient.setPcClientId(Long.parseLong(registerClientDTO.getPcClientId()));
        newClient.setToken(registerClientDTO.getToken());

        Client client = clientService.findByEmail(newClient.getEmail());

        if (client != null) {
            model.addAttribute("error", "Email already in use.");
            return "support";
        }
        if (clientService.insert(newClient) != null) {
        	
        	ResponseEntity<ClientInfoDto> response = null;

            try {
                response = restTemplate.getForEntity("https://localhost:8762/api/pc_info/payment-methods/updateClientsMethods/Bitcoin/" + registerClientDTO.getEmail(),
                		ClientInfoDto.class);

            } catch (Exception exception) {
                exception.printStackTrace();
                return ResponseEntity.badRequest().body("Could not contact payment-info.");
            }
        	
        	
            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", MessageFormat.format("http://localhost:4200/client", registerClientDTO.getPcClientId()));
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

        } else {
            model.addAttribute("registrationDTO", registerClientDTO);
            model.addAttribute("error", "Something went wrong, please try again.");
            return "support";
        }

    }
}
