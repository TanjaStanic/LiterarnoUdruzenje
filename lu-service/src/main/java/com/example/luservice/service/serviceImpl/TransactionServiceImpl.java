package com.example.luservice.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.model.Transaction;
import com.example.luservice.model.TransactionStatus;
import com.example.luservice.repository.TransactionRepository;
import com.example.luservice.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

	private TransactionRepository transactionRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Transaction initializeTransaction(PaymentRequestDTO pReqDTO) {
		Transaction t = new Transaction();

		t.setAmount(pReqDTO.getAmount());
		t.setMerchantOrderId(pReqDTO.getMerchantOrderId());
		t.setStatus(TransactionStatus.CREATED);
		
		t = transactionRepository.save(t);
		return t;
	}

	@Override
	public Transaction findByMerchantOrderId(long merchantOrderId) {
		return transactionRepository.findByMerchantOrderId(merchantOrderId);
	}

	@Override
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

}
