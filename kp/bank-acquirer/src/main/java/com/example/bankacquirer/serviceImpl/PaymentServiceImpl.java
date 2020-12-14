package com.example.bankacquirer.serviceImpl;

import com.example.bankacquirer.dto.PaymentConcentratorRequestDTO;
import com.example.bankacquirer.dto.PaymentConcentratorResponseDTO;
import com.example.bankacquirer.repository.PcRequestRepository;
import com.example.bankacquirer.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.bankacquirer.domain.PaymentConcentratorRequest;


public class PaymentServiceImpl implements PaymentService{

	@Autowired
	PcRequestRepository pcRequestRepository;
	
	private RestTemplate restTemplate;
	
	@Override
	public PaymentConcentratorResponseDTO createResponse(PaymentConcentratorRequestDTO pcRequestDTO) {
		
		PaymentConcentratorRequest pcReq = new PaymentConcentratorRequest(pcRequestDTO.getAmount(),
				pcRequestDTO.getMerchantId(), pcRequestDTO.getMerchantPassword(), pcRequestDTO.getMerchantOrderId(),
				pcRequestDTO.getMerchantTimestamp(), pcRequestDTO.getSuccessUrl(),
				pcRequestDTO.getFailedUrl(), pcRequestDTO.getErrorUrl());
		
		pcRequestRepository.save(pcReq);
		//restTemplate.
		return new PaymentConcentratorResponseDTO(pcReq.getId(),"https://localhost:8445/card-data");
	}

	

}
