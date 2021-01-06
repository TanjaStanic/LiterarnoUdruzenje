package com.example.bankpcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.example.bankpcc.domain.Bank;
import com.example.bankpcc.domain.PccRequest;
import com.example.bankpcc.dto.PccRequestDTO;
import com.example.bankpcc.dto.PccResponseDTO;
import com.example.bankpcc.repository.BankRepository;
import com.example.bankpcc.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/redirect-request", method = RequestMethod.POST)
	private ResponseEntity<?> redirectRequest(@RequestBody PccRequestDTO pccRequestDTO) {
		if (!paymentService.checkRequest(pccRequestDTO)) {
			return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		PccRequest pccRequest = paymentService.saveRequest(pccRequestDTO);
		
		String temp = pccRequest.getPanNumber().replace("-", "");
        String number = temp.substring(0, 6);
        
        Bank bank = new Bank();
        
        try {
        	bank = bankRepository.findOneByUniqueBankNumber(number);
        }
        catch (Exception e) {
        	return new ResponseEntity<>(pccRequestDTO, HttpStatus.NOT_FOUND);
        }
        
        ResponseEntity<PccResponseDTO> responseEntity = restTemplate.exchange(bank.getUrl(), HttpMethod.POST,
				new HttpEntity<PccRequestDTO>(pccRequestDTO), PccResponseDTO.class);
        
        System.out.println("Odgovor: ");
		System.out.println(responseEntity.getBody());
        
		return new ResponseEntity<>(pccRequestDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/create-response",method=RequestMethod.GET)
	private ResponseEntity<?> createResponse(){
		
		return new ResponseEntity<String>("Test PCC works", HttpStatus.OK);
	}
}
