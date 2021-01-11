package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.Client;
import com.example.paypalms.domain.Transaction;
import com.example.paypalms.dto.TransactionDto;
import com.example.paypalms.enums.TransactionStatus;
import com.example.paypalms.repository.TransactionRepository;
import com.example.paypalms.service.ClientService;
import com.example.paypalms.service.TransactionService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Log4j2
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private ClientService clientService;
    @Value("${execution.mode}")
    private String executionMode;
    private RestTemplate restTemplate;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientService clientService, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
        this.restTemplate = restTemplate;
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
     * Sync transaction statues with PayPal every half hour
     */
    //@Scheduled(initialDelay = 10000, fixedRate = 1800000)
    @Scheduled(initialDelay = 120000, fixedRate = 300000)
    @Async
    @Override
    public void syncTransactions() {
        List<Client> clients = (ArrayList) clientService.getAll();
        clients.stream().forEach(client -> {
            APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);
            List<Transaction> transactions = (List<Transaction>) transactionRepository.findAllByClientIdAndStatusIn(client.getId(),
                    Arrays.asList(TransactionStatus.COMPLETED, TransactionStatus.CREATED));
            if (!transactions.isEmpty()) {

                for (Transaction transaction : transactions) {
                    try {
                        Payment payment = Payment.get(context, transaction.getPaymentId());

                        System.out.println("KP: " + transaction.getStatus() + " | PP: " + payment.getState());
                        boolean statusChanged= false;
                        if (payment.getState().equalsIgnoreCase("APPROVED")) {
                            transaction.setStatus(TransactionStatus.SUCCESSFUL);
                            save(transaction);
                            statusChanged = true;
                        } else if (payment.getState().equalsIgnoreCase("FAILED")) {
                            transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
                            save(transaction);
                            statusChanged = true;
                        }
                        if (statusChanged){
                            sendTransactionUpdate(transaction);
                        }

                    } catch (PayPalRESTException e) {
                        //if transaction doesn't exist
                        if (e.getResponsecode() == 404) {
                            transaction.setStatus(TransactionStatus.CANCELED);
                            save(transaction);
                            sendTransactionUpdate(transaction);
                        }
                    }
                }
            }
        });
    }

    public void sendTransactionUpdate(Transaction transaction) {
        try {
            TransactionDto transactionDto = new TransactionDto(transaction.getClient().getEmail(), transaction.getStatus(),
                    transaction.getMerchantOrderId(), transaction.getPaymentId(), transaction.getAmount(), transaction.getCurrency().getCode());
            HttpEntity<TransactionDto> entity = new HttpEntity<>(transactionDto);
            restTemplate.exchange("https://localhost:8444/auth/transactions", HttpMethod.POST, entity, String.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("ERROR | Could not contact payment-info.");
            log.error(exception.getMessage());
        }
    }
}
