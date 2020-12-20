package com.example.paymentinfo.service.serviceImpl;

import com.example.paymentinfo.domain.Client;
import com.example.paymentinfo.domain.Transaction;
import com.example.paymentinfo.domain.TransactionStatus;
import com.example.paymentinfo.dto.PaymentRequestDTO;
import com.example.paymentinfo.repository.ClientRepository;
import com.example.paymentinfo.repository.TransactionRepository;
import com.example.paymentinfo.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private ClientRepository clientRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
		
		
		return null;
	}
}
