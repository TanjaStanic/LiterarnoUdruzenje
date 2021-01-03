package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.Currency;
import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.domain.TransactionStatus;
import com.example.paymentinfo.dto.BillingPlanDto;
import com.example.paymentinfo.dto.PaymentRequestDTO;
import com.example.paymentinfo.dto.SubscriptionPlanDto;
import com.example.paymentinfo.service.ClientService;
import com.example.paymentinfo.service.CurrencyService;
import com.example.paymentinfo.service.PaymentMethodService;

import com.example.paymentinfo.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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
    private TransactionService transactionService;
    private CurrencyService currencyService;

    public ViewController(TransactionService transactionService, ClientService clientService, RestTemplate restTemplate, CurrencyService currencyService) {
        this.transactionService = transactionService;
        this.clientService = clientService;
        this.restTemplate = restTemplate;
        this.currencyService = currencyService;
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

}
