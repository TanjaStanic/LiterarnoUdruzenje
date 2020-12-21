package com.example.paymentinfo.service.serviceImpl;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.domain.TransactionStatus;
import com.example.paymentinfo.dto.PaymentRequestDTO;
import com.example.paymentinfo.repository.ClientRepository;
import com.example.paymentinfo.repository.TransactionRepository;
import com.example.paymentinfo.service.TransactionService;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private ClientRepository clientRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientRepository clientRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findByPaymentID(String paymentId) {
        return transactionRepository.findByPaymentID(paymentId);
    }

	@Override
	public Transaction initializeTransaction(PaymentRequestDTO pReqDTO) {
		
		Client seller = clientRepository.findByEmail(pReqDTO.getMerchantEmail());
		Transaction t = new Transaction();
		t.setStatus(TransactionStatus.CREATED);
		t.setAmount(pReqDTO.getAmount());
		t.setSeller(seller);
		t.setMerchantOrderId(pReqDTO.getMerchantOrderId());
		t = transactionRepository.save(t);
        
		log.info("CREATED | Transaction | Transction Id: " + t.getId());
		return t;
	}

    @Override
    public Transaction findByMerchantOrderId(long merchantOrderId) {
        return transactionRepository.findByMerchantOrderId(merchantOrderId);
    }
}
