package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.dto.ClientBasicInfoDto;
import com.example.paymentinfo.dto.PaymentMethodDto;
import com.example.paymentinfo.repository.PaymentMethodRepository;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.PaymentMethodService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("payment-methods")
public class PaymentMethodController {

    private RestTemplate restTemplate;
    private PaymentMethodRepository paymentMethodRepository;
    private ClientService clientService;
    private PaymentMethodService paymentMethodService;

    public PaymentMethodController(RestTemplate restTemplate, PaymentMethodRepository paymentMethodRepository,
                                   ClientService clientService, PaymentMethodService paymentMethodService) {
        this.restTemplate = restTemplate;
        this.paymentMethodRepository = paymentMethodRepository;
        this.clientService = clientService;
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethods() {
        List<PaymentMethod> paymentMethods = (ArrayList) paymentMethodService.findAll();
        List<PaymentMethodDto> retVaL = paymentMethods.stream()
                .map(paymentMethod -> new PaymentMethodDto(paymentMethod))
                .collect(Collectors.toList());
        return ResponseEntity.ok(retVaL);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody @Valid PaymentMethodDto paymentMethodDto) {
        PaymentMethod entity = null;
        try {
            entity = new PaymentMethod(paymentMethodDto.getName(), paymentMethodDto.isSubscriptionSupported(), paymentMethodDto.getApplicationName());
            entity = paymentMethodService.save(entity);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body("Failed to insert payment method.");
        }

        if (entity != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Failed to insert payment method.");
        }

    }

    @PostMapping("/{paymentMethodId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody PaymentMethodDto paymentMethodDto, @PathVariable long paymentMethodId) {
        PaymentMethod entity = new PaymentMethod(paymentMethodDto.getName(), paymentMethodDto.isSubscriptionSupported(), paymentMethodDto.getApplicationName());
        entity.setId(paymentMethodId);
        try {
            paymentMethodService.update(entity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update payment method.");
        }

        List<PaymentMethodDto> retVaL = paymentMethodService.findAll().stream().map(paymentMethod -> new PaymentMethodDto(paymentMethod)).collect(Collectors.toList());
        return ResponseEntity.ok(retVaL);


    }

    @DeleteMapping("/{paymentMethodId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long paymentMethodId) {
        try {
            paymentMethodService.deleteById(paymentMethodId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to delete payment method.");
        }
        return ResponseEntity.ok().build();


    }

    @GetMapping("/register/{paymentMethod}/{clientId}")
    public ResponseEntity<?> redirectRegisterRequest(@PathVariable String paymentMethod, @PathVariable long clientId) {
        PaymentMethod method = paymentMethodRepository.findByApplicationName(paymentMethod);
        Client client = clientService.findById(clientId);

        ClientBasicInfoDto clientBasicInfoDto = new ClientBasicInfoDto(client.getName(), client.getEmail());
        ResponseEntity<String> redirectUrl = restTemplate.getForEntity(MessageFormat.format("https://{0}/auth/clients/register-url/{1}", paymentMethod, client.getId().toString()), String.class);

        if (redirectUrl.getStatusCode() == HttpStatus.OK) {
            client.getPaymentMethods().add(method);
            clientService.update(client);
            HttpHeaders headersRedirect = new HttpHeaders();
            headersRedirect.add("Location", redirectUrl.getBody());
            headersRedirect.add("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<>(null, headersRedirect, HttpStatus.FOUND);
        } else {
            // TODO redirect to error page
            return ResponseEntity.badRequest().build();
        }


    }


}
