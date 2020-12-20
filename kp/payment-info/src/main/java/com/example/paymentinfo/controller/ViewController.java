package com.example.paymentinfo.controller;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.PaymentMethod;
import com.example.paymentinfo.repository.ClientRepository;
import com.example.paymentinfo.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping("view")
public class ViewController {


    private PaymentMethodRepository paymentMethodRepository;
    private ClientRepository clientRepository;

    public ViewController(PaymentMethodRepository paymentMethodRepository, ClientRepository clientRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/payment-methods/{sellerEmail}/{merchantOrderId}")
    public String getPaymentMethods(Model model, @PathVariable String sellerEmail, @PathVariable long merchantOrderId) {
        Map<String, String> paymentMethods = new HashMap<>();
        Client client = clientRepository.findByEmail(sellerEmail);
        Set<PaymentMethod> clientPaymentMethods = client.getPaymentMethods();
        for (PaymentMethod method : clientPaymentMethods ){
            paymentMethods.put(method.getName(), "https://localhost:8444/api/"+ method.getApplicationName() + "/" + sellerEmail + "/" + merchantOrderId);
        }
        model.addAttribute("paymentMethods", paymentMethods);
        return "paymentMethods";
    }


}
