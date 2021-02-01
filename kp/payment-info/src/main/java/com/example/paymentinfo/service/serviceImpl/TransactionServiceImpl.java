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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;

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
		Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.CREATED);
        transaction.setAmount(pReqDTO.getAmount());
        transaction.setSeller(seller);
        transaction.setMerchantOrderId(pReqDTO.getMerchantOrderId());
        transaction.setCreated(new Date());
        transaction = transactionRepository.save(transaction);

		log.info("CREATED | Transaction | Transction Id: " + transaction.getId());
		return transaction;
	}

    @Override
    public Transaction findByMerchantOrderId(long merchantOrderId) {
        return transactionRepository.findByMerchantOrderId(merchantOrderId);
    }

    @Override
    public Transaction findById(long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Transaction with id {0} does not exist.", id)));

    }

    @Override
    public Collection<Transaction> findAllByStatusIn(Collection<TransactionStatus> statuses){
        return transactionRepository.findAllByStatusIn(statuses);
    }

    @Override
    public void saveRange(Collection<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }
}
