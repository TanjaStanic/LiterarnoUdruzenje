package com.example.bankpcc.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.bankpcc.domain.PccRequest;
import com.example.bankpcc.dto.PccRequestDTO;
import com.example.bankpcc.repository.PccRequestRepository;
import com.example.bankpcc.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	private PccRequestRepository pccRequestRepository;
	
	public PaymentServiceImpl(PccRequestRepository pccRequestRepository) {
		super();
		this.pccRequestRepository = pccRequestRepository;
	}

	@Override
	public void SendResponse() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkRequest(PccRequestDTO pccRequestDTO) {
		if (pccRequestDTO.getCardHolder()==null || pccRequestDTO.getPanNumber()==null ||
				pccRequestDTO.getCvv()==null  || pccRequestDTO.getMm()==null ||
				pccRequestDTO.getYy()==null || pccRequestDTO.getAcquirerTimespamp()==null ||
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
