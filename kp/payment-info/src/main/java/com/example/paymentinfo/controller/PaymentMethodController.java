package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.dto.ClientBasicInfoDto;
import com.example.paymentinfo.repository.PaymentMethodRepository;
import com.example.paymentinfo.service.ClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@Controller
@RequestMapping("payment-methods")
public class PaymentMethodController {

    private RestTemplate restTemplate;
    private PaymentMethodRepository paymentMethodRepository;
    private ClientService clientService;

    public PaymentMethodController(RestTemplate restTemplate, PaymentMethodRepository paymentMethodRepository, ClientService clientService) {
        this.restTemplate = restTemplate;
        this.paymentMethodRepository = paymentMethodRepository;
        this.clientService = clientService;
    }

    @GetMapping("/register/{paymentMethod}/{clientId}")
    public ResponseEntity<?> redirectRegisterRequest(@PathVariable String paymentMethod, @PathVariable long clientId) {
        PaymentMethod method = paymentMethodRepository.findByApplicationName(paymentMethod);
        Client client = clientService.findById(clientId);

        ClientBasicInfoDto clientBasicInfoDto = new ClientBasicInfoDto(client.getName(), client.getEmail());
        ResponseEntity<String> redirectUrl = restTemplate.getForEntity(MessageFormat.format("https://{0}/clients/register-url/{1}", paymentMethod, client.getId().toString()),String.class);

        if (redirectUrl.getStatusCode() == HttpStatus.OK){
            client.getPaymentMethods().add(method);
            clientService.update(client);
            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", redirectUrl.getBody());
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<>(null, headersRedirect, HttpStatus.FOUND);
        }else {
            // TODO redirect to error page
            return ResponseEntity.badRequest().build();
        }


    }


}
