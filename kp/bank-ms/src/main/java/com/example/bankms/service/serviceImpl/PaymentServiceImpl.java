package com.example.bankms.service.serviceImpl;

import com.example.bankms.domain.Client;
import com.example.bankms.domain.Currency;
import com.example.bankms.domain.Transaction;

import com.example.bankms.dto.*;
import com.example.bankms.enums.TransactionStatus;
import com.example.bankms.service.ClientService;
import com.example.bankms.service.CurrencyService;
import com.example.bankms.service.PaymentService;
import com.example.bankms.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private final ClientService clientService;
    private final TransactionService transactionService;
    private CurrencyService currencyService;
    private RestTemplate restTemplate;

    public PaymentServiceImpl(ClientService clientService, TransactionService transactionService,
                              CurrencyService currencyService, RestTemplate restTemplate) {
        this.clientService = clientService;
        this.transactionService = transactionService;
        this.currencyService = currencyService;
        this.restTemplate = restTemplate;
    }

    public String initiatePayment(PaymentRequestDTO request) {
        Client seller = clientService.findByEmail(request.getMerchantEmail());
        Currency currency = currencyService.findByCode(request.getCurrencyCode());
        if (seller != null) {
            Transaction transaction = new Transaction(seller, request.getMerchantOrderId(), TransactionStatus.CREATED, request.getAmount(), currency);
            if (transaction != null) {

                BankAcquirerRequestDTO bankRequest = new BankAcquirerRequestDTO(request.getAmount(), seller.getMerchantID(),
                        seller.getMerchantPassword(), request.getMerchantOrderId(), request.getMerchantTimestamp(),
                        request.getSuccessUrl(), request.getFailedUrl(), request.getErrorUrl(), request.getCurrencyCode());
                try {
                    ResponseEntity<BankAcquirerResponseDTO> responseDTO = restTemplate.postForEntity("https://localhost:8445/payment/create-response", bankRequest,
                            BankAcquirerResponseDTO.class);
                    transaction.setPaymentID(responseDTO.getBody().getPaymentId());
                    transaction = transactionService.save(transaction);
                    TransactionDto transactionDto = new TransactionDto(transaction.getSeller().getEmail(),
                            transaction.getStatus(), transaction.getMerchantOrderId(), Long.toString(transaction.getPaymentID()), transaction.getAmount(), transaction.getCurrency().getCode());
                    this.sendTransactionUpdate(transactionDto);

                    return responseDTO.getBody().getPaymentUrl();

                } catch (Exception e) {

                    transaction.setStatus(TransactionStatus.ERROR);
                    transaction = transactionService.save(transaction);
                    log.error("ERROR | Could not contact acquirer, transaction failed.");
                    log.error(e.getMessage());
                    TransactionDto transactionDto = new TransactionDto(transaction.getSeller().getEmail(),
                            transaction.getStatus(), transaction.getMerchantOrderId(), Long.toString(transaction.getPaymentID()), transaction.getAmount(), transaction.getCurrency().getCode());
                    this.sendTransactionUpdate(transactionDto);
                    throw new RuntimeException("Could not contact acquirer, transaction failed.");
                }

            }
        }
        log.error("ERROR | Seller does not exist in database.");
        throw new RuntimeException("Seller does not exist in database.");
    }

    @Override
    public void completePayment(CompletedPaymentDTO completedPayment) {
        Transaction transaction = transactionService.findByPaymentID(completedPayment.getPaymentID());
        transaction.setAcquirerOrderId(completedPayment.getAcquirerOrderID());
        transaction.setAcquirerTimestamp(completedPayment.getAcquirerTimestamp());
        transaction.setStatus(completedPayment.getTransactionStatus());
        transaction.setIssuerOrderID(completedPayment.getIssuerOrderID());
        transaction.setIssuerTimestamp(completedPayment.getIssuerTimestamp());
        transaction = transactionService.save(transaction);
        TransactionDto transactionDto = new TransactionDto(transaction.getSeller().getEmail(),
                transaction.getStatus(),transaction.getMerchantOrderId(), Long.toString(transaction.getPaymentID()), transaction.getAmount(), transaction.getCurrency().getCode());
        try {
            this.sendTransactionUpdate(transactionDto);
        } catch (Exception exception) {
            log.error("ERROR | Could not contact payment-info.");
            log.error(exception.getMessage());
        }

    }

    public void sendTransactionUpdate(TransactionDto transaction) {
        try {

            HttpEntity<TransactionDto> entity = new HttpEntity<>(transaction);

            restTemplate.exchange("https://localhost:8444/auth/transactions", HttpMethod.POST, entity, String.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("ERROR | Could not contact payment-info.");
            log.error(exception.getMessage());
        }


    }

}
