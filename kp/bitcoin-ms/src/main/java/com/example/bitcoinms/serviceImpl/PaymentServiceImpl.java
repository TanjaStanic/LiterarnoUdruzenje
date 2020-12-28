package com.example.bitcoinms.serviceImpl;

import java.util.Date;

import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	private final ClientService clientService;
	private final TransactionService transactionService;
    private CurrencyService currencyService;
    private RestTemplate restTemplate;
	
	public PaymentServiceImpl(ClientService clientService, TransactionService transactionService,
            CurrencyService currencyService, RestTemplate restTemplate) {
			this.clientService = clientService;
			this.transactionService = transactionService;
			this.currencyService = currencyService;
			this.restTemplate = restTemplate;
}
	
	@Override
	public String initiatePayment(PaymentRequestDTO request) {
	
		Client seller = clientService.findByEmail(request.getMerchantEmail());
		Currency currency = currencyService.findByCode(request.getCurrencyCode());
		if (seller == null || currency == null) {
            System.out.println("CANCELED | Bitcoin Payment | Amount: " + request.getAmount() + " " + request.getCurrencyCode());
            return null;
        }
		
		 Transaction transaction = new Transaction(request.getOrderId(),request.getMerchantOrderId(), seller,
	                TransactionStatus.NEW, request.getAmount(), currency, request.getSuccessUrl(),
	                request.getCallbackUrl(), request.getCancelUrl());
		 
	    transaction = transactionService.save(transaction);
	    
	    HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");

				
		//create order
		ResponseEntity<PaymentResponseDTO> resp = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders", transaction, PaymentResponseDTO.class);
        String url = resp.getBody().getPayment_url();
		
        transaction.setOrder_id(Long.valueOf(resp.getBody().getId()));
        Transaction savedOrder = this.transactionRepository.save(transaction);
        //checkOrderStatus(savedOrder);
        return url;
		
		          
		
	}

}
