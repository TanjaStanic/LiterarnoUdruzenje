package com.example.bitcoinms.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.bitcoinms.domain.*;
import com.example.bitcoinms.dto.*;
import com.example.bitcoinms.enums.TransactionStatus;
import com.example.bitcoinms.repository.TransactionRepository;
import com.example.bitcoinms.service.*;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private TransactionRepository transactionRepository;
    private final ClientService clientService;
    private final TransactionService transactionService;
    private CurrencyService currencyService;
    private RestTemplate restTemplate;

    private static final String CANCEL_URL = "https://localhost:8442/cancel";
    private static final String CALLBACK_URL = "https://localhost:8442/callback";
    private static final String SUCCESS_URL = "https://localhost:8442/success";

    public PaymentServiceImpl(ClientService clientService, TransactionService transactionService,
                              CurrencyService currencyService, RestTemplate restTemplate, TransactionRepository transactionRepository) {
        this.clientService = clientService;
        this.transactionService = transactionService;
        this.currencyService = currencyService;
        this.restTemplate = restTemplate;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String initiatePayment(PaymentRequestDTO request) {

        Client seller = clientService.findByEmail(request.getMerchantEmail());
        Currency currency = currencyService.findByCode(request.getCurrencyCode());
        if (seller == null || currency == null) {
            log.error("CANCELED | Bitcoin Payment | Amount: " + request.getAmount() + " " + request.getCurrencyCode());
            return null;
        }

        System.out.println("Payment request:    " + request.toString());


        String authToken = seller.getToken();
        System.out.println("Token is" + authToken);


        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setAmount(request.getAmount());
        transaction.setCallback_url(CALLBACK_URL);
        transaction.setCancel_url(request.getCancelUrl());
        transaction.setSuccess_url(request.getSuccessUrl());
        transaction.setError_url(request.getErrorUrl());
        transaction.setMerchantOrderId(request.getMerchantOrderId());
        transaction.setStatus(TransactionStatus.NEW);
        transaction.setPrice_currency(currency);
        transaction = transactionService.save(transaction);

        CoinGateRequestDto coinGateRequest = new CoinGateRequestDto();
        coinGateRequest.setCallback_url(CALLBACK_URL);
        coinGateRequest.setCancel_url(CANCEL_URL + "?id=" + transaction.getId().toString());
        coinGateRequest.setOrder_id("ORDER_" + transaction.getId().toString());
        coinGateRequest.setToken(authToken);
        coinGateRequest.setPrice_amount(request.getAmount());
        coinGateRequest.setPrice_currency(currency.getCode());
        coinGateRequest.setReceive_currency("BTC");
        coinGateRequest.setSuccess_url(SUCCESS_URL + "?id=" + transaction.getId().toString());

        System.out.println("TOKEN:  " + authToken);


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Authorization", "Bearer " + authToken);
        HttpEntity<CoinGateRequestDto> requestEntity = new HttpEntity<>(coinGateRequest, requestHeaders);

        System.out.println("POST FOR COINGATE");
        ResponseEntity<PaymentResponseDTO> response = null;
        try {
            //create order
            response = restTemplate.exchange("https://api-sandbox.coingate.com/v2/orders/", HttpMethod.POST, requestEntity, PaymentResponseDTO.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            transaction.setStatus(TransactionStatus.INVALID);
            transactionService.save(transaction);
            return request.getErrorUrl();
        }

        PaymentResponseDTO responseObject = response.getBody();

        transaction.setPaymentId(responseObject.getId());
        transaction.setStatus(TransactionStatus.valueOf(responseObject.getStatus().toUpperCase()));
        transaction.setOrder_id(coinGateRequest.getOrder_id());

        if (!responseObject.getReceive_amount().equals("")) {
            try {
                transaction.setAmount(Double.parseDouble(responseObject.getReceive_amount()));
            } catch (NumberFormatException e) {
                transaction.setAmount(request.getAmount());
            }
        }

        Currency recive_currency = currencyService.findByCode(responseObject.getReceive_currency());
        System.out.println(recive_currency.getName());
        transaction.setReceive_currency(recive_currency);

        String url = response.getBody().getPayment_url();
        Transaction savedOrder = this.transactionRepository.save(transaction);
        sendTransactionUpdate(new TransactionDto(savedOrder));
        return url;

    }

    @Override
    public String cancelPayment(Long id) {
        Transaction transaction;
        try {
            transaction = this.transactionService.findById(id);
        } catch (Exception exception) {
            return null;
        }
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Authorization", "Bearer " + transaction.getSeller().getToken());

        HttpEntity<PaymentRequestDTO> request = new HttpEntity<>(requestHeaders);
        ResponseEntity<PaymentResponseDTO> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://api-sandbox.coingate.com/v2/orders/" + transaction.getPaymentId(),
                    HttpMethod.GET, request, PaymentResponseDTO.class);
        } catch (Exception e) {

        }

        PaymentResponseDTO response = responseEntity.getBody();
        transaction.setPaymentId(response.getId());
        transaction.setStatus(TransactionStatus.valueOf(response.getStatus().toUpperCase()));
        if (!response.getReceive_amount().equals("")) {
            try {
                transaction.setAmount(Double.parseDouble(response.getReceive_amount()));
            } catch (NumberFormatException e) {

            }
        }
        Currency recive_currency = currencyService.findByCode(response.getReceive_currency());
        transaction.setReceive_currency(recive_currency);
        transaction = this.transactionService.save(transaction);
        sendTransactionUpdate(new TransactionDto(transaction));

        return transaction.getCancel_url();
    }

    @Override
    public String paymentSuccess(Long id) {
        Transaction transaction;
        try {
            transaction = this.transactionService.findById(id);
        } catch (Exception exception) {
            return null;
        }
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Authorization", "Bearer " + transaction.getSeller().getToken());

        HttpEntity<PaymentRequestDTO> request = new HttpEntity<>(requestHeaders);
        ResponseEntity<PaymentResponseDTO> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://api-sandbox.coingate.com/v2/orders/" + transaction.getPaymentId(),
                    HttpMethod.GET, request, PaymentResponseDTO.class);
        } catch (Exception e) {

        }

        PaymentResponseDTO response = responseEntity.getBody();
        transaction.setPaymentId(response.getId());
        transaction.setStatus(TransactionStatus.valueOf(response.getStatus().toUpperCase()));
        if (!response.getReceive_amount().equals("")) {
            try {
                transaction.setAmount(Double.parseDouble(response.getReceive_amount()));
            } catch (NumberFormatException e) {

            }
        }
        Currency receive_currency = currencyService.findByCode(response.getReceive_currency());
        transaction.setReceive_currency(receive_currency);
        transaction = this.transactionService.save(transaction);
        sendTransactionUpdate(new TransactionDto(transaction));
        return transaction.getSuccess_url();
    }

    public void sendTransactionUpdate(TransactionDto transaction) {
        try {
            HttpEntity<TransactionDto> entity = new HttpEntity<>(transaction);
            restTemplate.exchange("https://localhost:8444/auth/transactions", HttpMethod.POST, entity, String.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("ERROR | Could not contact payment-info.");
            log.error(exception.getMessage());
        }
    }
}
