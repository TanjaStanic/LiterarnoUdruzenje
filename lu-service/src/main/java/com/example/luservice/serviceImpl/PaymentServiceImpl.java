package com.example.luservice.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.model.Client;
import com.example.luservice.model.FormData;
import com.example.luservice.model.PaymentData;
import com.example.luservice.model.PaymentMethod;
import com.example.luservice.repository.ClientRepository;
import com.example.luservice.repository.PaymentDataRepository;
import com.example.luservice.repository.TransactionRepository;
import com.example.luservice.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	 @Autowired
	 private ClientRepository sellerRepository;
	 
	 @Autowired
	 private PaymentDataRepository paymentDataRepository;
	 
	 @Autowired
	 private TransactionRepository transactionRepository;
	
	 private RestTemplate restTemplate;
	
	 @Override
	 public PaymentRequestDTO createPaymentRequest(String clientID, double amount) {
		PaymentRequestDTO pReqDto = new PaymentRequestDTO();
		Client seller = sellerRepository.findClientById(Long.parseLong(clientID));
		
		//generate 10 numbers id
		long generatedId = 0;

		generatedId = (long)Math.floor(Math.random()*9_000_000_000L)+1_000_000_000L;

		pReqDto.setMerchantOrderId(generatedId);
		pReqDto.setAmount(amount);
		pReqDto.setSuccessUrl("https://localhost:8447/view/success");
		pReqDto.setErrorUrl("https://localhost:8447/view/error");
		pReqDto.setFailedUrl("https://localhost:8447/view/fail");
		pReqDto.setMerchantTimestamp(new Date());
		pReqDto.setMerchantEmail(seller.getEmail());
		
		try {	
			ResponseEntity<String> response = restTemplate.exchange("https://localhost:8444/api/initiate-payment-request", HttpMethod.POST,
					new HttpEntity<PaymentRequestDTO>(pReqDto), String.class);
		} catch (Exception e) {
			System.out.println("Could not contact complete-payment in bankMS");
	    }

		return pReqDto;
	}
	 
	 
	@Override
    public boolean newSellerPaymentMethods(List<PaymentMethod> paymentMethods, Long userId) {

        Client seller = this.sellerRepository.findClientByUserId(userId);
        if (seller == null) {
            seller = new Client();
            seller.setId(userId);
        }

        if (seller.getPaymentMethods() == null) {
            seller.setPaymentMethods(new ArrayList<>());
        }
        if (seller.getPaymentsData() == null) {
            seller.setPaymentsData(new ArrayList<>());
        }

        for (PaymentMethod paymentMethod : paymentMethods) {
            List<PaymentData> paymentsData = new ArrayList<>();
            boolean valid = true;
            for (FormData formData : paymentMethod.getRequiredFormData()) {
                if (formData.getValue() != null && !formData.getValue().equals("")) {
                    paymentsData.add(new PaymentData(formData.getCode(), formData.getValue()));
                } else {
                    valid = false;
                }
            }
            if (paymentsData.size() != 0 && valid == true) {
                seller.getPaymentMethods().add(paymentMethod);
                seller.getPaymentsData().addAll(paymentsData);      // doda sva polja potrebna za banku npr
            }
        }

        List<PaymentData> paymentData = seller.getPaymentsData();
        seller.setPaymentsData(null);
        this.sellerRepository.save(seller);

        for (int i = 0; i < paymentData.size(); i++) {
            PaymentData pd = new PaymentData();
            pd.setName(paymentData.get(i).getName());
            pd.setValue(paymentData.get(i).getValue());
            paymentData.set(i, this.paymentDataRepository.save(pd));
        }

        seller.setPaymentsData(paymentData);
        this.sellerRepository.save(seller);

        return true;
    }


}
