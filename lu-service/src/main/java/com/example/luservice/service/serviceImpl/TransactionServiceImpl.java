package com.example.luservice.service.serviceImpl;

import com.example.luservice.model.Client;
import com.example.luservice.model.Currency;
import com.example.luservice.service.ClientService;
import com.example.luservice.service.CurrencyService;
import org.springframework.stereotype.Service;

import com.example.luservice.dto.PaymentRequestDTO;
import com.example.luservice.model.Transaction;
import com.example.luservice.model.TransactionStatus;
import com.example.luservice.repository.TransactionRepository;
import com.example.luservice.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private ClientService clientService;
    private CurrencyService currencyService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientService clientService, CurrencyService currencyService) {
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
        this.currencyService = currencyService;
    }

    @Override
    public Transaction initializeTransaction(PaymentRequestDTO pReqDTO) {
        Transaction transaction = new Transaction();
        Client client = clientService.findByEmail(pReqDTO.getMerchantEmail());
        Currency currency = currencyService.findByCode(pReqDTO.getCurrencyCode());
        transaction.setClient(client);
        transaction.setCurrency(currency);
        transaction.setAmount(pReqDTO.getAmount());
        transaction.setMerchantOrderId(pReqDTO.getMerchantOrderId());
        transaction.setStatus(TransactionStatus.CREATED);

        return transactionRepository.save(transaction);
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
