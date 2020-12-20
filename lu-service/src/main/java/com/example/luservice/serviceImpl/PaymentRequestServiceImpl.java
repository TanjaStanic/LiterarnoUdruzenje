package com.example.luservice.serviceImpl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.luservice.model.Client;
import com.example.luservice.model.PaymentRequest;
import com.example.luservice.repository.ClientRepository;
import com.example.luservice.repository.PaymentRequestRepository;
import com.example.luservice.service.PaymentRequestService;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {
   
	@Autowired
    private PaymentRequestRepository paymentRequestRepository;
	
    @Autowired
    private ClientRepository clientRepository;
    
	@Override
	public PaymentRequest getPaymentRequest(Long merchantOrderId) {
		// TODO Auto-generated method stub
        return paymentRequestRepository.findByMerchantOrderId(merchantOrderId);
	}


	@Override
	public PaymentRequest createPaymentRequest(String clientID, double amount) {
		// TODO Auto-generated method stub
		 Client foundSeller = clientRepository.findClientById(Long.parseLong(clientID));

	     PaymentRequest paymentRequest = new PaymentRequest();
//	     paymentRequest.setMerchantId(foundSeller.getMerchantId());
	     paymentRequest.setMerchantPassword("123");

	     Random random = new Random();
	     paymentRequest.setMerchantOrderId((long)random.nextInt());
	     paymentRequest.setAmount(amount);
	     paymentRequest.setMerchantTimestamp(new Date());
	     paymentRequest.setSuccessUrl("/success");
	     paymentRequest.setErrorUrl("/error");
	     paymentRequest.setFailedUrl("/fail");


	     paymentRequestRepository.save(paymentRequest);

	     return paymentRequest;
	}
	
	/*@Override
	public PaymentRequest createPaymentRequest(String clientID, double amount) {
		// TODO Auto-generated method stub
		 Client foundSeller = clientRepository.findClientById(Long.parseLong(clientID));

	     PaymentRequest paymentRequest = new PaymentRequest();
//	     paymentRequest.setMerchantId(foundSeller.getMerchantId());
	     paymentRequest.setMerchantPassword("123");

	     Random random = new Random();
	     paymentRequest.setMerchantOrderId((long)random.nextInt());
	     paymentRequest.setAmount(amount);
	     paymentRequest.setMerchantTimestamp(new Date());
	     paymentRequest.setSuccessUrl("/success");
	     paymentRequest.setErrorUrl("/error");
	     paymentRequest.setFailedUrl("/fail");


	     paymentRequestRepository.save(paymentRequest);

	     return paymentRequest;
	}*/

}
