package com.example.bankpcc.service;

import org.springframework.stereotype.Service;

import com.example.bankpcc.domain.PccRequest;
import com.example.bankpcc.dto.PccRequestDTO;
import com.example.bankpcc.dto.PccResponseDTO;

@Service
public interface PaymentService {

	public boolean checkRequest(PccRequestDTO pccRequestDTO);
	public PccRequest saveRequest(PccRequestDTO pccRequestDTO);
	PccResponseDTO sendResponse(PccRequestDTO pccRequestDTO);
}
