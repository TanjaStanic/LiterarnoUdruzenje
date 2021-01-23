package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.dto.BillingPlanDto;
import com.example.paymentinfo.dto.ClientRegistrationDto;
import com.example.paymentinfo.dto.SubscriptionPlanDto;
import com.example.paymentinfo.service.ClientService;

import com.example.paymentinfo.service.PaymentMethodService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("view")
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class ViewController {

    private ClientService clientService;
    private RestTemplate restTemplate;
    private PaymentMethodService paymentMethodService;

    public ViewController(ClientService clientService, RestTemplate restTemplate, PaymentMethodService paymentMethodService) {
        this.clientService = clientService;
        this.restTemplate = restTemplate;
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("/payment-methods/{sellerEmail}/{merchantOrderId}")
    public String getPaymentMethods(Model model, @PathVariable String sellerEmail, @PathVariable long merchantOrderId) {
        Map<String, String> paymentMethods;
        Client client = clientService.findByEmail(sellerEmail);

        paymentMethods = client.getPaymentMethods().stream()
                .collect(Collectors.toMap(
                        PaymentMethod::getName,
                        method -> MessageFormat.format("https://localhost:8444/auth/api/{0}/{1}/{2}", method.getApplicationName(), sellerEmail, String.valueOf(merchantOrderId))));

        model.addAttribute("paymentMethods", paymentMethods);
        return "paymentMethods";
    }

    @GetMapping("/subscriptions/payment-methods/{sellerEmail}/{transactionId}")
    public String getSubscriptionPaymentMethodForSeller(Model model, @PathVariable String sellerEmail, @PathVariable long transactionId) {

        Map<String, String> paymentMethods;
        Client client = clientService.findByEmail(sellerEmail);
        paymentMethods = client.getPaymentMethods().stream()
                .filter(method -> method.isSubscriptionSupported())
                .collect(Collectors.toMap(
                        PaymentMethod::getName,
                        method -> MessageFormat.format("https://localhost:8444/view/subscription-plans/{0}/{1}/{2}", String.valueOf(transactionId), method.getApplicationName(), sellerEmail)));

        model.addAttribute("paymentMethods", paymentMethods);

        return "paymentMethods";
    }

    @GetMapping("/subscription-plans/{transactionId}/{paymentMethod}/{sellerEmail}")
    public String getSubscriptionPlansForSeller(Model model, @PathVariable String transactionId, @PathVariable String paymentMethod, @PathVariable String sellerEmail) {
        List<SubscriptionPlanDto> subscriptionPlans;

        BillingPlanDto billingPlanDto = new BillingPlanDto();
        billingPlanDto.setTransactionId(transactionId);
        billingPlanDto.setPaymentMethod(paymentMethod);

        model.addAttribute("billingPlan", billingPlanDto);

        ResponseEntity<SubscriptionPlanDto[]> response = restTemplate.getForEntity(
                MessageFormat.format("https://{0}/subscriptions/{1}", paymentMethod, sellerEmail), SubscriptionPlanDto[].class);
        subscriptionPlans = Arrays.asList(response.getBody());

        model.addAttribute("plans", subscriptionPlans);

        return "choose-subscription-plan";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("client", new ClientRegistrationDto());
        return "registration";
    }


    @GetMapping("/select-payment-methods/{clientId}")
    public String getSelectPaymentMethodsForm(Model model, @PathVariable long clientId) {
        Map<String, String> paymentMethods;
        Client client = clientService.findById(clientId);
        List<PaymentMethod> allMethods = (List<PaymentMethod>) paymentMethodService.findAll();
        allMethods.removeAll(client.getPaymentMethods());

        paymentMethods = allMethods.stream()
                .collect(Collectors.toMap(
                        PaymentMethod::getName,
                        method -> MessageFormat.format("https://localhost:8444/payment-methods/register/{0}/{1}", method.getApplicationName(), String.valueOf(clientId))));

        model.addAttribute("paymentMethods", paymentMethods);
        return "select-payment-methods";
    }

    @GetMapping("/register/finish")
    public String getFinishRegistrationPage(){
        return "registration-complete";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/login-error")
    public String getLoginErrorPage(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }



    @GetMapping("/payment-methods")
    public String getPaymentMethods(){
        return "payment-methods";
    }
}
