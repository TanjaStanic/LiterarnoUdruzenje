package com.example.bankacquirer.service;

import com.example.bankacquirer.dto.PaymentConcentratorResponseDTO;

import org.springframework.stereotype.Service;

import com.example.bankacquirer.dto.PaymentConcentratorRequestDTO;

@Service
public interface PaymentService {
	
	public PaymentConcentratorResponseDTO createResponse(PaymentConcentratorRequestDTO pcRequestDTO);
}
