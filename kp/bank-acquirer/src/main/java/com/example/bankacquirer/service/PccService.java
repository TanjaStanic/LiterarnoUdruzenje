package com.example.bankacquirer.service;

import org.springframework.stereotype.Service;

import com.example.bankacquirer.dto.PccRequestDTO;
import com.example.bankacquirer.dto.PccResponseDTO;

@Service
public interface PccService {

	 PccResponseDTO createResponse(PccRequestDTO pccRequestDto);
}
