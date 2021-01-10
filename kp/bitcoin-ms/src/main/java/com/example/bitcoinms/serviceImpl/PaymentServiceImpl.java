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
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	private final ClientService clientService;
	private final TransactionService transactionService;
	
    private CurrencyService currencyService;
   	private RestTemplate restTemplate;
   	
   	private static final String CANCEL_URL="https://localhost:8447/view/cancel";
   	private static final String CALLBACK_URL="https://localhost:8447/view/error";
	
	public PaymentServiceImpl(ClientService clientService, TransactionService transactionService,
            CurrencyService currencyService, RestTemplate restTemplate) {
			this.clientService = clientService;
			this.transactionService = transactionService;
			this.currencyService = currencyService;
			this.restTemplate = restTemplate;
}
	
	public String initiatePayment(PaymentRequestDTO request) {
		
		Client seller = clientService.findByEmail(request.getMerchantEmail());
		Currency currency = currencyService.findByCode(request.getCurrencyCode());
		if (seller == null || currency == null) {
            System.out.println("CANCELED | Bitcoin Payment | Amount: " + request.getAmount() + " " + request.getCurrencyCode());
            return null;
        }
		
		System.out.println("Price currency is" + currency.getCode());
		System.out.println("Order id is" + request.getOrderId());
		System.out.println("Merchant Order id is" + request.getMerchantOrderId());
		System.out.println("Seller is" + seller.getName());
		System.out.println("Amoount is" + request.getAmount());
		System.out.println("Success url" + request.getSuccessUrl());
		System.out.println("Callback url" + request.getCallbackUrl());
		System.out.println("Cancel url" + request.getCancelUrl());
		
		Currency recive_currency = currencyService.findById((long) 5);
		System.out.println(recive_currency.getName());
        		
		String authToken = seller.getToken();
		System.out.println("Token is" + authToken);


		
		 Transaction transaction = new Transaction(request.getMerchantOrderId(), seller,
	                TransactionStatus.NEW, request.getAmount(), currency, recive_currency, request.getSuccessUrl(),
	                CALLBACK_URL, CANCEL_URL,authToken);
		 
	    transaction = transactionService.save(transaction);
	    System.out.println(transaction.getToken() + "TOKEN");
		System.out.println("Sacuvao" + transaction);
		

		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        //requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", "Bearer "+ authToken);

        //requestHeaders.setBearerAuth(authToken);

        HttpEntity<Transaction> requestEntity = new HttpEntity<>(transaction, requestHeaders);


		System.out.println("POST FOR COINGATE");		
		//create order
		ResponseEntity<PaymentResponseDTO> resp = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders", requestEntity, PaymentResponseDTO.class);

		//ResponseEntity<PaymentResponseDTO> resp = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders", requestEntity, PaymentResponseDTO.class);
		System.out.println("Prosao");
		String url = resp.getBody().getPayment_url();
		
        transaction.setOrder_id(Long.valueOf(resp.getBody().getId()));
        Transaction savedOrder = this.transactionRepository.save(transaction);
        //checkOrderStatus(savedOrder);
        return url;
		
		          
		
	}

}
