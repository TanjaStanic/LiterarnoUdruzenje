package com.example.paymentinfo.controller;

import com.example.paymentinfo.dto.PaymentRequestDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;

@RestController
public class TestController {

    private final RestTemplate restTemplate;

    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<String> testRouting() {
        return ResponseEntity.ok().body("I am payment-info-ms");
    }

/*    @GetMapping("test")
    public ResponseEntity<String> testBankMS(){
        System.out.println("Payment info");
        String fromBankMs = restTemplate.getForObject("https://bank-ms/test", String.class);
        fromBankMs+=", BankMS responded Payment info";
        return ResponseEntity.ok().body(fromBankMs);

    }*/

    @GetMapping("test")
    public ResponseEntity<?> paypalPayment() {
        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setAmount(15);
        requestDTO.setCurrencyCode("USD");
        requestDTO.setErrorUrl("https://:localhost:8762/api/paypal/error");
        requestDTO.setFailedUrl("https://:localhost:8762/api/paypal/error");
        requestDTO.setSuccessUrl("https://:localhost:8762/api/paypal/succes");
        requestDTO.setMerchantEmail("sb-zx3ys4123984@business.example.com");
        requestDTO.setMerchantOrderId(1234);
        requestDTO.setMerchantTimestamp(ZonedDateTime.now());
        ResponseEntity<String> fromPaypalMs = restTemplate.postForEntity("https://paypal-ms/", requestDTO, String.class);

        HttpHeaders headersRedirect = new HttpHeaders();
        headersRedirect.add("Location", fromPaypalMs.getBody());
        headersRedirect.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);

    }


}
