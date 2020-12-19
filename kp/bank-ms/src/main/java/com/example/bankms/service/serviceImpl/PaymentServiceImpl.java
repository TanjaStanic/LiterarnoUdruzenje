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
                    ResponseEntity<BankAcquirerResponseDTO> responseDTO = restTemplate.postForEntity("https://localhost:8445/payment/create-response", bankRequest,
                            BankAcquirerResponseDTO.class);
                    transaction.setPaymentID(responseDTO.getBody().getPaymentId());
                    transactionService.save(transaction);
                    return responseDTO.getBody().getPaymentUrl();

                } catch (Exception e) {

                    transaction.setStatus(TransactionStatus.ERROR);
                    transactionService.save(transaction);
                    log.error("ERROR | Could not contact acquirer, transaction failed.");
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
        transactionService.save(transaction);

    }

}
