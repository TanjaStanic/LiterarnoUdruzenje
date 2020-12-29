package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.Client;
import com.example.paypalms.domain.Transaction;
import com.example.paypalms.enums.TransactionStatus;
import com.example.paypalms.repository.TransactionRepository;
import com.example.paypalms.service.ClientService;
import com.example.paypalms.service.TransactionService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private ClientService clientService;
    @Value("${execution.mode}")
    private String executionMode;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientService clientService) {
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findById(Long id) {
        Transaction found = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Transaction with id {0} does not exist.", id)));
        return found;
    }

    @Override
    public Transaction findByPaymentId(String paymentId) {
        return transactionRepository.findByPaymentId(paymentId);
    }

    /**
     * Sync transaction statues with PayPal every hour
     */
    //@Scheduled(initialDelay = 10000, fixedRate = 3600000)
    //@Scheduled(initialDelay = 10000, fixedRate = 180000)
    @Scheduled(initialDelay = 120000, fixedRate = 300000)
    @Async
    @Override
    public void syncTransactions() {
        List<Client> clients = (ArrayList) clientService.getAll();
        clients.stream().forEach(client -> {
            APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);
            List<Transaction> transactions = (List<Transaction>) transactionRepository.findAllByClientIdAndStatus(client.getId(), TransactionStatus.COMPLETED);
            if (!transactions.isEmpty()) {

                for (Transaction transaction : transactions) {
                    try {
                        Payment payment = Payment.get(context, transaction.getPaymentId());

                        System.out.println("KP: " + transaction.getStatus() + " | PP: " + payment.getState());

                        if (payment.getState().equalsIgnoreCase("APPROVED")) {
                            transaction.setStatus(TransactionStatus.SUCCESSFUL);
                            save(transaction);
                        } else if (payment.getState().equalsIgnoreCase("FAILED")) {
                            transaction.setStatus(TransactionStatus.FAILED);
                            save(transaction);
                        }

                    } catch (PayPalRESTException e) {
                        //if transaction doesn't exist
                        if (e.getResponsecode() == 404) {
                            transaction.setStatus(TransactionStatus.CANCELED);
                            save(transaction);
                        }
                    }
                }
            }
        });
    }
}
