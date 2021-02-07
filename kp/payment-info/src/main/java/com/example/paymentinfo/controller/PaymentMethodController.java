package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.dto.ClientBasicInfoDto;
import com.example.paymentinfo.dto.ClientDto;
import com.example.paymentinfo.dto.PaymentMethodDto;
import com.example.paymentinfo.repository.PaymentMethodRepository;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.PaymentMethodService;
import com.example.paymentinfo.utils.PaymentMethodValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("payment-methods")
public class PaymentMethodController {

    private RestTemplate restTemplate;
    private PaymentMethodRepository paymentMethodRepository;
    private ClientService clientService;
    private PaymentMethodService paymentMethodService;
    private PaymentMethodValidator paymentMethodValidator;

    public PaymentMethodController(RestTemplate restTemplate, PaymentMethodRepository paymentMethodRepository,
                                   ClientService clientService, PaymentMethodService paymentMethodService,
                                   PaymentMethodValidator paymentMethodValidator) {
        this.restTemplate = restTemplate;
        this.paymentMethodRepository = paymentMethodRepository;
        this.clientService = clientService;
        this.paymentMethodService = paymentMethodService;
        this.paymentMethodValidator = paymentMethodValidator;
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
    public ResponseEntity<?> create(@RequestBody @Valid PaymentMethodDto paymentMethodDto, BindingResult bindingResult) {
        PaymentMethod entity = null;

        paymentMethodValidator.validate(paymentMethodDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Failed to insert payment method.");
        }

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

    @PutMapping("/{paymentMethodId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable long paymentMethodId, @RequestBody PaymentMethodDto paymentMethodDto, BindingResult bindingResult) {
        paymentMethodValidator.validate(paymentMethodDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Failed to update payment method.");
        }

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
    
    @GetMapping("/clientsMethods/{clientId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<PaymentMethodDto>> getClientPaymentMethods(@PathVariable long clientId) {
        List<PaymentMethod> paymentMethods = (ArrayList) paymentMethodRepository.findAllByClients_id(clientId);
        List<PaymentMethodDto> retVaL = paymentMethods.stream()
                .map(paymentMethod -> new PaymentMethodDto(paymentMethod))
                .collect(Collectors.toList());
        return ResponseEntity.ok(retVaL);
    }
   
    @GetMapping("/noClientsMethods/{clientId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<PaymentMethodDto>> getNoClientPaymentMethods(@PathVariable long clientId) {
        List<PaymentMethod> paymentMethods = (ArrayList) paymentMethodService.findAllNoSupportedByClient(clientId);
        List<PaymentMethodDto> retVaL = paymentMethods.stream()
                .map(paymentMethod -> new PaymentMethodDto(paymentMethod))
                .collect(Collectors.toList());
        return ResponseEntity.ok(retVaL);
    }

    @GetMapping("delete/{userId}/{paymentMethodId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> deleteFromUser(@PathVariable long userId,@PathVariable long paymentMethodId) {
        try {
        	paymentMethodService.deleteOneFromUser(userId, paymentMethodId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to delete payment method from user.");
        }
        return ResponseEntity.ok().build();

    }
    
    @GetMapping("add/{userId}/{paymentMethodId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> addNewMethod(@PathVariable long userId,@PathVariable long paymentMethodId) {
        try {
        	paymentMethodService.addPaymetMethodToClient(userId, paymentMethodId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to add new payment method. ");
        }
        return ResponseEntity.ok().build();


    }
    @GetMapping("/updateClientsMethods/{paymentMethod}/{clientEmail}")
    public ResponseEntity<?> updateClient(@PathVariable String paymentMethod,
    			@PathVariable String clientEmail){
    	System.out.println("tu saaaaaaaaaaaaaaam");
    	try {
    		clientService.updatePaymentMethod(paymentMethod, clientEmail);
    	} catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to update new payment method. ");
        }

    	return ResponseEntity.ok().build();
    }

}
