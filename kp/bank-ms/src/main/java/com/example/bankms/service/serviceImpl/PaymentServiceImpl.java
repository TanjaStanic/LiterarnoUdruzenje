package com.example.bankms.service.serviceImpl;

import com.example.bankms.domain.Client;
import com.example.bankms.domain.Currency;
import com.example.bankms.domain.Transaction;

import com.example.bankms.dto.BankAcquirerRequestDTO;
import com.example.bankms.dto.BankAcquirerResponseDTO;
import com.example.bankms.dto.CompletedPaymentDTO;
import com.example.bankms.dto.PaymentRequestDTO;
import com.example.bankms.enums.TransactionStatus;
import com.example.bankms.service.ClientService;
import com.example.bankms.service.CurrencyService;
import com.example.bankms.service.PaymentService;
import com.example.bankms.service.TransactionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
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

    public String initiatePayment(PaymentRequestDTO request){
        Client seller = clientService.findByEmail(request.getMerchantEmail());
        Currency currency = currencyService.findByCode(request.getCurrencyCode());
        if (seller != null) {
            Transaction transaction = new Transaction(seller, request.getMerchantOrderId(), TransactionStatus.CREATED, request.getAmount(), currency);
            if (transaction != null) {

                BankAcquirerRequestDTO bankRequest = new BankAcquirerRequestDTO(request.getAmount(), seller.getMerchantID(),
                                seller.getMerchantPassword(), request.getMerchantOrderId(), request.getMerchantTimestamp(),
                                request.getSuccessUrl(), request.getFailedUrl(), request.getErrorUrl(), currency);
                try {
                    ResponseEntity<BankAcquirerResponseDTO> responseDTO = restTemplate.postForEntity("https://localhost:8445", bankRequest,
                            BankAcquirerResponseDTO.class);
                    transaction.setPaymentID(responseDTO.getBody().getPaymentId());
                    transactionService.save(transaction);
                    return responseDTO.getBody().getPaymentUrl() + responseDTO.getBody().getPaymentId();

                } catch (Exception e) {

                    transaction.setStatus(TransactionStatus.ERROR);
                    transactionService.save(transaction);
                    throw new RuntimeException("Coud not cpntact acquirer, transaction failed.");
                }

            }
        }

        throw new RuntimeException("Coud not cpntact acquirer, transaction failed.");
    }

    @Override
    public void completePayment(CompletedPaymentDTO completedPayment) {
        Transaction transaction = transactionService.findByPaymentID(completedPayment.getPaymentID());
        transaction.setAcquirerOrderId(completedPayment.getAcquirerOrderID());
        transaction.setAcquirerTimestamp(completedPayment.getAcquirerTimestamp());
        transaction.setStatus(completedPayment.getTransactionStatus());
        transactionService.save(transaction);
        try {

            HttpEntity<String> request = new HttpEntity<>(completedPayment.getUrl());
            ResponseEntity<String> response = restTemplate
                    .exchange("https://payment-info/", HttpMethod.POST, request, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Coud not cpntact payment-info");
        }
    }

}
