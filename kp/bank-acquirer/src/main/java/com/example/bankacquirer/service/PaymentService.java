package com.example.bankacquirer.service;

import com.example.bankacquirer.dto.PaymentConcentratorResponseDTO;

import org.springframework.stereotype.Service;

import com.example.bankacquirer.dto.CardDataDTO;
import com.example.bankacquirer.dto.PaymentConcentratorRequestDTO;

public interface PaymentService {

    boolean checkRequest(PaymentConcentratorRequestDTO pcRequestDTO);

    PaymentConcentratorResponseDTO createResponse(PaymentConcentratorRequestDTO pcRequestDTO);

    String confirmPayment(CardDataDTO cardDataDTO, Long pcRequestId);

}
