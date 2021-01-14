package com.example.bankpcc.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankpcc.domain.Transaction;
import com.example.bankpcc.domain.TransactionStatus;
import com.example.bankpcc.dto.PccRequestDTO;
import com.example.bankpcc.dto.PccResponseDTO;
import com.example.bankpcc.repository.TransactionRepository;
import com.example.bankpcc.service.TransactionService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepository transactionRepository;
	
	
	@Override
	public Transaction createTransaction(PccRequestDTO pccRequestDTO) {
        Transaction transaction = new Transaction();
        
        long generatedId = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        transaction.setId(generatedId);
        transaction.setCardHolder(pccRequestDTO.getCardHolder());
        transaction.setAcquirerOrderId(pccRequestDTO.getAcquirerOrderId());
        transaction.setAcquirerTimestamp(pccRequestDTO.getAcquirerTimestamp());
        transaction.setAmount(pccRequestDTO.getAmount());
        transaction.setStatus(TransactionStatus.CREATED);
        
        transaction = transactionRepository.save(transaction);
        log.error("INFO | Transaction created");
        return transaction;
	}

	@Override
	public Transaction updateTransaction(PccResponseDTO pccResponseDTO, Long id) {
		Transaction transaction = transactionRepository.findOneById(id);
		transaction.setIsAuthentificated(pccResponseDTO.getIsAuthentificated());
		transaction.setIsAutorized(pccResponseDTO.getIsAutorized());
		transaction.setIssuerOrderId(pccResponseDTO.getIssuerOrderId());
		transaction.setIssuerTimestamp(pccResponseDTO.getIssuerTimestamp());
		if (pccResponseDTO.getIsAuthentificated() && pccResponseDTO.getIsAutorized()) {
			transaction.setStatus(TransactionStatus.SUCCESSFUL);
			log.info("INFO | Transaction completed");
		}
		else if (!pccResponseDTO.getIsAuthentificated() ) {
			transaction.setStatus(TransactionStatus.ERROR);
			log.info("ERROR | Transaction error | Not authentificated. ");
		}
		else if (!pccResponseDTO.getIsAutorized()) {
			transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
			log.info("CANCELED | Transaction canceled | Not autorized. ");
		}
		else {
			transaction.setStatus(TransactionStatus.ERROR);
			log.info("ERROR | Transaction error");
		}
		
		transaction = transactionRepository.save(transaction);
		log.error("INFO | Transaction updated");
		return transaction;
	}

}
