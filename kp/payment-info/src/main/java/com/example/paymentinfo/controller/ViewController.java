package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.dto.BillingPlanDto;
import com.example.paymentinfo.dto.SubscriptionPlanDto;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.PaymentMethodService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
@RequestMapping("view")
public class ViewController {

    private ClientService clientService;
    private PaymentMethodService paymentMethodService;
    private RestTemplate restTemplate;

    public ViewController(PaymentMethodService paymentMethodService, ClientService clientService, RestTemplate restTemplate) {
        this.paymentMethodService = paymentMethodService;
        this.clientService = clientService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/payment-methods/{sellerEmail}/{merchantOrderId}")
    public String getPaymentMethods(Model model, @PathVariable String sellerEmail, @PathVariable long merchantOrderId) {
        Map<String, String> paymentMethods;
        Client client = clientService.findByEmail(sellerEmail);

        paymentMethods = client.getPaymentMethods().stream()
                .collect(Collectors.toMap(
                        PaymentMethod::getName,
                        method -> MessageFormat.format("https://localhost:8444/api/{0}/{1}/{2}", method.getApplicationName(), sellerEmail, String.valueOf(merchantOrderId))));

        model.addAttribute("paymentMethods", paymentMethods);
        return "paymentMethods";
    }

    @GetMapping("/subscriptions/payment-methods/{sellerEmail}")
    public String getSubscriptionPaymentMethodForSeller(Model model, @PathVariable String sellerEmail) {
        Map<String, String> paymentMethods;
        Client client = clientService.findByEmail(sellerEmail);

        paymentMethods = client.getPaymentMethods().stream()
                .filter(method -> method.isSubscriptionSupported())
                .collect(Collectors.toMap(
                        PaymentMethod::getName,
                        method -> MessageFormat.format("https://localhost:8444/view/subscription-plans/{0}/{1}", sellerEmail, method.getApplicationName())));

        model.addAttribute("paymentMethods", paymentMethods);

        return "paymentMethods";
    }

    @GetMapping("/subscription-plans/{sellerEmail}/{paymentMethod}")
    public String getSubscriptionPlansForSeller(Model model, @PathVariable String sellerEmail, @PathVariable String paymentMethod) {
        List<SubscriptionPlanDto> subscriptionPlans;

        BillingPlanDto billingPlanDto = new BillingPlanDto();
        billingPlanDto.setSellerEmail(sellerEmail);
        billingPlanDto.setPaymentMethod(paymentMethod);

        model.addAttribute("billingPlan", billingPlanDto);

        ResponseEntity<SubscriptionPlanDto[]> response = restTemplate.getForEntity(
                MessageFormat.format("https://{0}/subscriptions/{1}", paymentMethod, sellerEmail), SubscriptionPlanDto[].class);
        subscriptionPlans = Arrays.asList(response.getBody());

        model.addAttribute("plans", subscriptionPlans);

        return "choose-subscription-plan";
    }

}
