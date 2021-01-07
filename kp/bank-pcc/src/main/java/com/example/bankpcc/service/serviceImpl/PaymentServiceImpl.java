package com.example.bankpcc.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.bankpcc.domain.Bank;
import com.example.bankpcc.domain.PccRequest;
import com.example.bankpcc.dto.PccRequestDTO;
import com.example.bankpcc.dto.PccResponseDTO;
import com.example.bankpcc.repository.BankRepository;
import com.example.bankpcc.repository.PccRequestRepository;
import com.example.bankpcc.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	private PccRequestRepository pccRequestRepository;
	private BankRepository bankRepository;
	private RestTemplate restTemplate;
	
	
	@Autowired
	public PaymentServiceImpl(PccRequestRepository pccRequestRepository, BankRepository bankRepository,
			RestTemplate restTemplate) {
		super();
		this.pccRequestRepository = pccRequestRepository;
		this.bankRepository = bankRepository;
		this.restTemplate = restTemplate;
	}

	@Override
	public PccResponseDTO sendResponse(PccRequestDTO pccRequestDTO) {

		
		String temp = pccRequestDTO.getPanNumber().replace("-", "");
        String number = temp.substring(0, 6);
        Bank bank = new Bank();
        
        try {
        	bank = bankRepository.findOneByUniqueBankNumber(number);
        	System.out.println("Pronasao banku " + bank.getUrl());
        }
        catch (Exception e) {
        	System.out.println("Count find bank!");        
		}
		PccResponseDTO response = new PccResponseDTO();

        try {
            response = restTemplate.postForObject(bank.getUrl(), pccRequestDTO, PccResponseDTO.class);
            System.out.println("Test pcc auth: " + response.getIsAuthentificated());
            System.out.println("Test pcc aut: " + response.getIsAutorized());
        } catch (Exception e) {
            System.out.println("Could not contact bank issuer");
        }
		return response;
	}

	@Override
	public boolean checkRequest(PccRequestDTO pccRequestDTO) {
		if (pccRequestDTO.getCardHolder()==null || pccRequestDTO.getPanNumber()==null ||
				pccRequestDTO.getCvv()==null  || pccRequestDTO.getMm()==null ||
				pccRequestDTO.getYy()==null || pccRequestDTO.getAcquirerTimestamp()==null ||
				pccRequestDTO.getAcquirerOrderId()==0)  {
			
			//put log here
            System.out.println("Some of requests parameters are missing");
			return false;
		}
		if (pccRequestDTO.getAmount()<=0) {
			//put log here
            System.out.println("Amount can not be negative or zero");
            return false;
		}
		return true;
	}

	@Override
	public PccRequest saveRequest(PccRequestDTO pccRequestDTO) {
		PccRequest pccRequest = new PccRequest(pccRequestDTO);
		pccRequest = pccRequestRepository.save(pccRequest);
		//log here	
		return pccRequest;
	}

}
