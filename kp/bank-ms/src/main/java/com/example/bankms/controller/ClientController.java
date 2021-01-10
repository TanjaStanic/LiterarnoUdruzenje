package com.example.bankms.controller;

import com.example.bankms.domain.Bank;
import com.example.bankms.domain.Client;
import com.example.bankms.dto.ClientCredentials;
import com.example.bankms.dto.ClientInfoDto;
import com.example.bankms.dto.RegisterClientDTO;
import com.example.bankms.repository.BankRepository;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private RestTemplate restTemplate;
    private final BankRepository bankRepository;

    public ClientController(ClientService clientService, RestTemplate restTemplate, BankRepository bankRepository) {
        this.clientService = clientService;
        this.restTemplate = restTemplate;
        this.bankRepository = bankRepository;
    }

    @GetMapping("/register-url/{clientId}")
    public @ResponseBody
    ResponseEntity<String> getRegistrationUrl(@PathVariable String clientId) {
        return ResponseEntity.ok(MessageFormat.format("https://localhost:8441/clients/register/{0}", clientId));
    }

    @GetMapping("/register/{clientId}")
    public Object registerForm(Model model, @PathVariable String clientId) {
        Client client = new Client();
        ResponseEntity<ClientInfoDto> clientInfo = restTemplate.getForEntity(
                "https://localhost:8762/api/pc_info/auth/clients/" + clientId,
                ClientInfoDto.class);

        if (clientInfo.getStatusCode() == HttpStatus.OK){
            client.setEmail(clientInfo.getBody().getEmail());
            client.setName(clientInfo.getBody().getName());
        }else {
            return ResponseEntity.badRequest().body(clientInfo.getBody());
        }

        client.setPcClientId(Long.parseLong(clientId));
        client = clientService.insert(client);

        Map<Long, String> banks = bankRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Bank::getId,
                        Bank::getName));

        model.addAttribute("banks", banks);

        RegisterClientDTO registerClientDTO = new RegisterClientDTO();
        registerClientDTO.setClientId(client.getId());
        model.addAttribute("registrationDTO", registerClientDTO);
        return "registration";

    }

    @PostMapping("/register")
    public Object register(@Valid @ModelAttribute("registrationDTO") RegisterClientDTO registerClientDTO, Model model) {

        Client client = clientService.findById(registerClientDTO.getClientId());
        Bank bank = bankRepository.findById(registerClientDTO.getBankId()).get();

        HttpEntity<ClientInfoDto> entity = new HttpEntity<>(new ClientInfoDto(client.getEmail(), client.getName()));
        ResponseEntity<ClientCredentials> response = restTemplate.postForEntity(
                bank.getUrl(), entity, ClientCredentials.class);

        ClientCredentials clientCredentials = response.getBody();
        client.setMerchantID(clientCredentials.getMerchantID());
        client.setMerchantPassword(clientCredentials.getMerchantPassword());
        client = clientService.update(client);

        String redirectUrl;
        if (response.getStatusCode() == HttpStatus.OK) {
            redirectUrl = MessageFormat.format("https://localhost:8762/api/pc_info/view/select-payment-methods/{0}", client.getPcClientId());
            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", redirectUrl);
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
        } else {
            return ResponseEntity.badRequest().body(response.getBody());
        }

    }

}
