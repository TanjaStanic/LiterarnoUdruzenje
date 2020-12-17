package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.Client;
import com.example.paypalms.domain.Currency;
import com.example.paypalms.domain.Transaction;
import com.example.paypalms.dto.PaymentRequestDTO;
import com.example.paypalms.enums.TransactionStatus;
import com.example.paypalms.service.ClientService;
import com.example.paypalms.service.CurrencyService;
import com.example.paypalms.service.PaymentService;
import com.example.paypalms.service.TransactionService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private ClientService clientService;
    private TransactionService transactionService;
    private CurrencyService currencyService;
    @Value("${execution.mode}")
    private String executionMode;

    public PaymentServiceImpl(ClientService clientService, TransactionService transactionService, CurrencyService currencyService) {
        this.clientService = clientService;
        this.transactionService = transactionService;
        this.currencyService = currencyService;
    }

    @Override
    public String createPayment(PaymentRequestDTO paymentRequest) throws PayPalRESTException {
        log.info("INITIATED | PayPal Payment | Amount: " + paymentRequest.getAmount());
        Client client = clientService.findByEmail(paymentRequest.getSellerEmail());
        Currency currency = currencyService.findByCode(paymentRequest.getCurrency());
        if (client == null || currency == null) {
            log.error("CANCELED | PayPal Payment | Amount: " + paymentRequest.getAmount() + " " + paymentRequest.getCurrency());
            return null;
        }
        Transaction transaction = new Transaction(paymentRequest.getMerchantOrderId(), client, new Date(),
                TransactionStatus.INITIATED, paymentRequest.getAmount(), currency, paymentRequest.getSuccessUrl(),
                paymentRequest.getErrorUrl(), paymentRequest.getFailedUrl());
        transaction = transactionService.save(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("https://localhost:8762/api/paypal/cancel?transactionId=" + transaction.getId());
        redirectUrls.setReturnUrl("https://localhost:8443/api/paypal/finish");

        Amount amount = new Amount();
        amount.setCurrency(paymentRequest.getCurrency());
        amount.setTotal(paymentRequest.getAmount().toString());

        com.paypal.api.payments.Transaction paypalTransaction = new com.paypal.api.payments.Transaction();
        paypalTransaction.setAmount(amount);
        paypalTransaction.setDescription("Payment for client with the email: " + client.getEmail());

        List<com.paypal.api.payments.Transaction> transactions = new ArrayList<>();
        transactions.add(paypalTransaction);

        Payment payment = new Payment("sale", payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);

        String redirectUrl = "";

        try {
            Payment newPayment = payment.create(context);
            //get the approval url from the response
            Iterator links = newPayment.getLinks().iterator();
            while (links.hasNext()) {
                Links link = (Links) links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    redirectUrl = link.getHref();
                    break;
                }
            }
            transaction.setPaymentId(newPayment.getId());
        } catch (PayPalRESTException exception) {
            transaction.setStatus(TransactionStatus.CANCELED);
            transactionService.save(transaction);
            throw exception;
        }
        transaction.setStatus(TransactionStatus.CREATED);
        transactionService.save(transaction);
        log.info("CREATED | PayPal Payment | Amount: " + paymentRequest.getAmount());
        //redirect the customer to the paypal site
        return redirectUrl;
    }

    @Override
    public String finishPayment(String paymentId, String payerId) {

        Transaction transaction = transactionService.findByPaymentId(Long.parseLong(paymentId));
        if (transaction != null) {
            Payment payment = new Payment();
            payment.setId(paymentId);

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerId);

            Client client = transaction.getClient();

            APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);
            try {
                Payment executedPayment = payment.execute(context, paymentExecution);
            } catch (PayPalRESTException exception) {
                transaction.setStatus(TransactionStatus.CANCELED);
                transactionService.save(transaction);
                log.error("CANCELED | PayPal Payment Execution");
                return transaction.getErrorUrl();
            }
            transaction.setStatus(TransactionStatus.COMPLETED);
            transactionService.save(transaction);
            log.info("COMPLETED | PayPal Payment Execution");
            return transaction.getSuccessUrl();
        }
        return null;
    }

    @Override
    public String cancelPayment(Long transactionId) {
        Transaction transaction;
        try {
            transaction = transactionService.findById(transactionId);
        } catch (RuntimeException exception) {
            log.error("ERROR | TRANSACTION WITH ID: " + transactionId + " NOT FOUND");
            return null;
        }
        return transaction.getFailedUrl();
    }
}
