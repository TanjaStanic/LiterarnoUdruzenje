package com.example.bankpcc.service;


import com.example.bankpcc.domain.Transaction;
import com.example.bankpcc.dto.PccRequestDTO;
import com.example.bankpcc.dto.PccResponseDTO;

public interface TransactionService {
	Transaction createTransaction(PccRequestDTO pccRequestDTO);
	Transaction updateTransaction(PccResponseDTO pccResponseDTO, Long id);
}
