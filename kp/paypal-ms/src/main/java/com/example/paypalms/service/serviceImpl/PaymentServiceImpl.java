package com.example.paypalms.service.serviceImpl;

import com.example.paypalms.domain.*;
import com.example.paypalms.domain.Currency;
import com.example.paypalms.domain.Transaction;
import com.example.paypalms.dto.PaymentRequestDTO;
import com.example.paypalms.dto.SubscriptionRequestDto;
import com.example.paypalms.dto.TransactionDto;
import com.example.paypalms.dto.UserSubscriptionDto;
import com.example.paypalms.enums.SubscriptionStatus;
import com.example.paypalms.enums.TransactionStatus;
import com.example.paypalms.service.*;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    private ClientService clientService;
    private TransactionService transactionService;
    private CurrencyService currencyService;
    private SubscriptionService subscriptionService;
    private SubscriptionPlanService subscriptionPlanService;
    @Value("${execution.mode}")
    private String executionMode;
    @Value("${success.url_agreement}")
    private String successAgreementURL;
    @Value("${cancel.url_agreement}")
    private String cancelAgreementUrl;
    private RestTemplate restTemplate;

    public PaymentServiceImpl(ClientService clientService, TransactionService transactionService,
                              CurrencyService currencyService, SubscriptionService subscriptionService,
                              SubscriptionPlanService subscriptionPlanService, RestTemplate restTemplate) {
        this.clientService = clientService;
        this.transactionService = transactionService;
        this.currencyService = currencyService;
        this.subscriptionService = subscriptionService;
        this.subscriptionPlanService = subscriptionPlanService;
        this.restTemplate = restTemplate;
    }

    @Override
    public String createPayment(PaymentRequestDTO paymentRequest) throws PayPalRESTException {
        log.info("INITIATED | PayPal Payment | Amount: " + paymentRequest.getAmount());
        Client client = clientService.findByEmail(paymentRequest.getMerchantEmail());
        Currency currency = currencyService.findByCode(paymentRequest.getCurrencyCode());
        if (client == null || currency == null) {
            log.error("CANCELED | PayPal Payment | Amount: " + paymentRequest.getAmount() + " " + paymentRequest.getCurrencyCode());
            return null;
        }
        Transaction transaction = new Transaction(paymentRequest.getMerchantOrderId(), client, new Date(),
                TransactionStatus.INITIATED, paymentRequest.getAmount(), currency, paymentRequest.getSuccessUrl(),
                paymentRequest.getErrorUrl(), paymentRequest.getFailedUrl());

        transaction.setCancelUrl(paymentRequest.getCancelUrl());
        transaction = transactionService.save(transaction);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setSellerEmail(paymentRequest.getMerchantEmail());
        transactionDto.setStatus(transaction.getStatus());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setCurrencyCode(transaction.getCurrency().getCode());
        transactionDto.setMerchantOrderId(transaction.getMerchantOrderId());

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("https://localhost:8443/cancel?transactionId=" + transaction.getId());
        redirectUrls.setReturnUrl("https://localhost:8443/finish");

        Amount amount = new Amount();
        amount.setCurrency(paymentRequest.getCurrencyCode());
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
            transactionDto.setPaymentID(newPayment.getId());
            transactionDto.setMerchantOrderId(transaction.getMerchantOrderId());

        } catch (PayPalRESTException exception) {
            transaction.setStatus(TransactionStatus.CANCELED);
            transaction = transactionService.save(transaction);
            transactionDto.setStatus(transaction.getStatus());
            transactionDto.setMerchantOrderId(transaction.getMerchantOrderId());
            this.sendTransactionUpdate(transactionDto);
            throw exception;
        }
        transaction.setStatus(TransactionStatus.CREATED);
        transaction = transactionService.save(transaction);
        log.info("CREATED | PayPal Payment | Amount: " + paymentRequest.getAmount());
        transactionDto.setStatus(transaction.getStatus());
        transactionDto.setMerchantOrderId(transaction.getMerchantOrderId());

        this.sendTransactionUpdate(transactionDto);
        //redirect the customer to the paypal site
        return redirectUrl;
    }

    @Override
    public String finishPayment(String paymentId, String payerId) {

        Transaction transaction = transactionService.findByPaymentId(paymentId);
        if (transaction != null) {
            Payment payment = new Payment();
            payment.setId(paymentId);

            String retUrl = null;
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerId);
            Client client = transaction.getClient();
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setSellerEmail(client.getEmail());
            transactionDto.setStatus(transaction.getStatus());
            transactionDto.setAmount(transaction.getAmount());
            transactionDto.setCurrencyCode(transaction.getCurrency().getCode());
            transactionDto.setPaymentID(transaction.getPaymentId());
            transactionDto.setMerchantOrderId(transaction.getMerchantOrderId());

            APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);
            // For negative testing
            //context.addHTTPHeader("PayPal-Mock-Response", "{\"mock_application_codes\": \"INSUFFICIENT_FUNDS\"}");
            try {
                Payment executedPayment = payment.execute(context, paymentExecution);

                if (executedPayment.getState().equalsIgnoreCase("APPROVED")) {
                    transaction.setStatus(TransactionStatus.SUCCESSFUL);
                    retUrl = transaction.getSuccessUrl();
                   
                } else if (executedPayment.getState().equalsIgnoreCase("FAILED")) {
                    transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
                    retUrl = transaction.getFailedUrl();
                }
            } catch (PayPalRESTException exception) {
                log.error(exception.getMessage());
                transaction.setStatus(TransactionStatus.UNSUCCESSFUL);
                retUrl = transaction.getFailedUrl();
            }

            transaction = transactionService.save(transaction);
            log.info("COMPLETED | PayPal Payment Execution");
            transactionDto.setPaymentID(transaction.getPaymentId());
            transactionDto.setStatus(transaction.getStatus());
            transactionDto.setMerchantOrderId(transaction.getMerchantOrderId());
            this.sendTransactionUpdate(transactionDto);
            return retUrl;
        }
        return null;
    }

    @Override
    public String cancelPayment(Long transactionId) {
        Transaction transaction;
        try {
            transaction = transactionService.findById(transactionId);
            transaction.setStatus(TransactionStatus.CANCELED);
            transactionService.save(transaction);

            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setSellerEmail(transaction.getClient().getEmail());
            transactionDto.setStatus(transaction.getStatus());
            transactionDto.setAmount(transaction.getAmount());
            transactionDto.setCurrencyCode(transaction.getCurrency().getCode());
            transactionDto.setPaymentID(transaction.getPaymentId());
            transactionDto.setMerchantOrderId(transaction.getMerchantOrderId());
            this.sendTransactionUpdate(transactionDto);

        } catch (RuntimeException exception) {
            log.error("ERROR | TRANSACTION WITH ID: " + transactionId + " NOT FOUND");
            return null;
        }
        return transaction.getCancelUrl();
    }

    @Override
    public Long createBillingPlan(SubscriptionRequestDto subscriptionRequest) throws PayPalRESTException {

        log.info("INITIATED | PayPal Subscription Payment | Amount: " + subscriptionRequest.getAmount());
        Client client = clientService.findByEmail(subscriptionRequest.getSellerEmail());
        if (client == null) {
            log.error("CANCELED | PayPal Subscription Payment | Amount: " + subscriptionRequest.getAmount() + " " + subscriptionRequest.getCurrencyCode());
            throw new RuntimeException("Client not found.");
        }
        Currency c = currencyService.findByCode(subscriptionRequest.getCurrencyCode());

        //create and save a new subscription with status INITIATED
        Subscription subscription = new Subscription(subscriptionRequest, client, c);
        Subscription savedSubscription = subscriptionService.save(subscription);

        SubscriptionPlan subscriptionPlan;
        try {
            subscriptionPlan = subscriptionPlanService.findById(subscriptionRequest.getSubscriptionPlan());
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            throw exception;
        }

        Double price = subscriptionPlan.getFrequency().getName().equals("YEAR") ? 12 * subscriptionRequest.getAmount() : subscriptionRequest.getAmount();
        price = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
        System.out.println(price);

        com.paypal.api.payments.Currency currency = new com.paypal.api.payments.Currency();
        currency.setValue(price.toString());
        currency.setCurrency(subscriptionRequest.getCurrencyCode());

        PaymentDefinition paymentDefinition = new PaymentDefinition();
        paymentDefinition.setName(client.getEmail() + " subscription");
        paymentDefinition.setType("REGULAR");
        paymentDefinition.setFrequency(subscriptionPlan.getFrequency().getName());
        paymentDefinition.setFrequencyInterval("1");
        paymentDefinition.setCycles(subscriptionPlan.getCyclesNumber().toString());

        paymentDefinition.setAmount(currency);

        List<PaymentDefinition> paymentDefinitionList = new ArrayList<PaymentDefinition>();
        paymentDefinitionList.add(paymentDefinition);

        MerchantPreferences merchantPreferences = new MerchantPreferences(cancelAgreementUrl + savedSubscription.getId(), successAgreementURL + savedSubscription.getId());
        merchantPreferences.setAutoBillAmount("YES");
        merchantPreferences.setInitialFailAmountAction("CONTINUE");

        Plan plan = new Plan();
        plan.setType(subscriptionPlan.getType().getName());
        plan.setName(client.getEmail() + " subscription");
        plan.setDescription(subscriptionRequest.getAmount() + " " + currency.getCurrency() + " a " + subscriptionPlan.getFrequency().getName().toLowerCase());

        plan.setPaymentDefinitions(paymentDefinitionList);
        plan.setMerchantPreferences(merchantPreferences);

        APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);

        try {

            Plan createdPlan = plan.create(context);
            //update plan state to ACTIVE
            List<Patch> patchRequestList = new ArrayList<Patch>();
            Map<String, String> value = new HashMap<String, String>();
            value.put("state", "ACTIVE");

            Patch patch = new Patch();
            patch.setPath("/");
            patch.setValue(value);
            patch.setOp("replace");
            patchRequestList.add(patch);

            //activate the plan
            createdPlan.update(context, patchRequestList);

            //save billing plan id
            savedSubscription.setBillingPlanId(createdPlan.getId());
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }

        subscriptionService.save(savedSubscription);

        return savedSubscription.getId();
    }

    @Override
    public String createBillingAgreement(SubscriptionRequestDto subscriptionRequest, Long subscriptionId) throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException {

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, 1);

        //ISO8601
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formattedDate = sdf.format(c.getTime());

        Subscription subscription;
        SubscriptionPlan subscriptionPlan;
        try {
            subscription = subscriptionService.findById(subscriptionId);
            subscriptionPlan = subscriptionPlanService.findById(subscriptionRequest.getSubscriptionPlan());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }

        if (subscriptionPlan.getType().getName().equals("INFINITE")){
            subscription.setExpirationDate(null);
        }else {
            if (subscriptionPlan.getFrequency().getName().equalsIgnoreCase("YEAR")){
                c.add(Calendar.YEAR, subscriptionPlan.getCyclesNumber());
            }else if (subscriptionPlan.getFrequency().getName().equalsIgnoreCase("MONTH")){
                c.add(Calendar.MONTH, subscriptionPlan.getCyclesNumber());
            }else if(subscriptionPlan.getFrequency().getName().equalsIgnoreCase("WEEK")){
                c.add(Calendar.WEEK_OF_MONTH, subscriptionPlan.getCyclesNumber());
            }else if(subscriptionPlan.getFrequency().getName().equalsIgnoreCase("DAY")){
                c.add(Calendar.DAY_OF_MONTH, subscriptionPlan.getCyclesNumber());
            }
            subscription.setExpirationDate(c.getTime());
        }

        Client client = clientService.findByEmail(subscriptionRequest.getSellerEmail());
        if (client == null) {
            log.error("CANCELED | PayPal Subscription Payment | Amount: " + subscriptionRequest.getAmount() + " " + subscriptionRequest.getCurrencyCode());
            log.error(MessageFormat.format("Seller with email {0} not found", subscriptionRequest.getSellerEmail()));
            subscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
            subscription = subscriptionService.save(subscription);
            this.sendSubscriptionUpdate(subscription);
            throw new RuntimeException("Seller not found");
        }
        //set billing plan id
        Plan plan = new Plan();
        plan.setId(subscription.getBillingPlanId());

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        //create the agreement object
        Agreement agreement = new Agreement();
        agreement.setName(client.getEmail() + " subscription");
        agreement.setDescription(MessageFormat.format("{0} {1} a {2}",subscription.getPaymentAmount(), subscription.getCurrency().getCode(), subscriptionPlan.getFrequency().getName().toLowerCase()));
        agreement.setStartDate(formattedDate);
        agreement.setPlan(plan);
        agreement.setPayer(payer);


        //save subscription with status BILLING_AGREEMENT_INITIATED
        subscription.setSubscriptionStatus(SubscriptionStatus.BILLING_AGREEMENT_INITIATED);
        Subscription savedSubscription = subscriptionService.save(subscription);

        APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);

        String redirectUrl = "";

        try {
            //create the agreement
            Agreement newAgreement = agreement.create(context);

            if (newAgreement != null) {
                //get the approval url from the response
                Iterator links = newAgreement.getLinks().iterator();

                while (links.hasNext()) {
                    Links link = (Links) links.next();

                    if (link.getRel().equalsIgnoreCase("approval_url")) {
                        redirectUrl = link.getHref();
                        break;
                    }
                }
            }
        } catch (PayPalRESTException | MalformedURLException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
            savedSubscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
            subscription = subscriptionService.save(subscription);
            this.sendSubscriptionUpdate(subscription);
            throw e;
        }

        //set subscription status to BILLING_AGREEMENT_CREATED
        savedSubscription.setSubscriptionStatus(SubscriptionStatus.BILLING_AGREEMENT_CREATED);
        subscriptionService.save(savedSubscription);
        log.info("SUBSCRIPTION ID: " + subscriptionId.toString() + "    BILLING_AGREEMENT_CREATED");
        //to redirect the customer to the paypal site
        return redirectUrl;
    }

    @Override
    public String executeBillingAgreement(long subscriptionId, String token) throws PayPalRESTException {
        Subscription subscription;
        try {
            subscription = subscriptionService.findById(subscriptionId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }

        Agreement agreement = new Agreement();
        agreement.setToken(token);

        Client client = subscription.getSeller();

        APIContext context = new APIContext(client.getClientId(), client.getClientSecret(), executionMode);
        //context.addHTTPHeader("PayPal-Mock-Response", "{\"mock_application_codes\": \"PAYER_CANNOT_PAY\"}");

        try {
            //execute the agreement and sign up the user for the subscription
            Agreement createdAgreement = agreement.execute(context, agreement.getToken());
            subscription.setBillingAgreementId(createdAgreement.getId());


        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
            subscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
            subscriptionService.save(subscription);
            sendSubscriptionUpdate(subscription);
            return subscription.getFailedUrl();
        }

        subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        subscriptionService.save(subscription);
        log.info("SUBSCRIPTION ID: " + subscriptionId + "   ACTIVE");
        sendSubscriptionUpdate(subscription);
        return subscription.getSuccessUrl();
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

    private void sendSubscriptionUpdate(Subscription subscription){
        UserSubscriptionDto subscriptionDto = new UserSubscriptionDto( subscription.getSeller().getEmail(), subscription.getMerchantOrderId(),
                subscription.getSubscriptionStatus(), subscription.getExpirationDate());
        try {
            HttpEntity<UserSubscriptionDto> entity = new HttpEntity<>(subscriptionDto);
            restTemplate.exchange("https://localhost:8444/auth/subscriptions/update", HttpMethod.POST, entity, String.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("ERROR | Could not contact payment-info.");
            log.error(exception.getMessage());
        }
    }
}
