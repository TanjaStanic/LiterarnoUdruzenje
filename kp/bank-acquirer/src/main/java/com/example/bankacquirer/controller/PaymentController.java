package com.example.bankacquirer.controller;

import com.example.bankacquirer.domain.Client;
import com.example.bankacquirer.domain.PaymentConcentratorRequest;
import com.example.bankacquirer.domain.Transaction;
import com.example.bankacquirer.dto.TransactionDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.bankacquirer.dto.CardDataDTO;
import com.example.bankacquirer.dto.PaymentConcentratorRequestDTO;
import com.example.bankacquirer.dto.PaymentConcentratorResponseDTO;
import com.example.bankacquirer.service.PaymentService;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class PaymentController {

    private PaymentService paymentService;
    private RestTemplate restTemplate;

    public PaymentController(PaymentService paymentService, RestTemplate restTemplate) {
        this.paymentService = paymentService;
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/create-response", method = RequestMethod.POST)
    private ResponseEntity<?> createResponse(@RequestBody PaymentConcentratorRequestDTO paymentConcentratorRequestDTO) {

        if (!paymentService.checkRequest(paymentConcentratorRequestDTO)) {
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        PaymentConcentratorResponseDTO pcResponseDTO = paymentService.createResponse(paymentConcentratorRequestDTO);

        return new ResponseEntity<>(pcResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/confirm-payment/{pcRequestId}", method = RequestMethod.POST)
    private ResponseEntity<?> confirmPayment(@RequestBody CardDataDTO cardDataDto, @PathVariable Long pcRequestId) {

        System.out.println("info: " + cardDataDto.getCardHolder() + ", " + cardDataDto.getCvv() + ", " + cardDataDto.getPanNumber() + ", "
                + cardDataDto.getMm() + ", " + cardDataDto.getYy());

        String redirectUrl = paymentService.confirmPayment(cardDataDto, pcRequestId);
        if (redirectUrl != null) {
            String responseUrl = sendPaymentResponse(pcRequestId, redirectUrl);
            if (responseUrl != null) {
                return new ResponseEntity<>(responseUrl, HttpStatus.OK);
            }
        }
        return ResponseEntity.badRequest().build();

    }


    private String sendPaymentResponse(long pcRequestId, String url) {
        PaymentConcentratorRequest request = paymentService.findById(pcRequestId);
        if (request == null) {
            return null;
        }
        Transaction transaction = null;
        try {
            transaction = paymentService.findTransactionByMerchantOrderId(request.getMerchantOrderId());
            HttpEntity<TransactionDto> entity = new HttpEntity<>(new TransactionDto(transaction));
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


}
