package com.example.paypalms.service;


import com.example.paypalms.domain.Subscription;
import com.example.paypalms.dto.PaymentRequestDTO;
import com.example.paypalms.dto.SubscriptionRequestDto;
import com.paypal.base.rest.PayPalRESTException;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

public interface PaymentService {

    String createPayment(PaymentRequestDTO paymentRequest) throws PayPalRESTException;

    String finishPayment(String paymentId, String PayerID);

    String cancelPayment(Long transactionId);

    Long createBillingPlan(SubscriptionRequestDto subscriptionRequest) throws PayPalRESTException;

    String createBillingAgreement(SubscriptionRequestDto subscriptionRequestDto, Long subscriptionId) throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException;

    String executeBillingAgreement(Subscription subscription, String token) throws PayPalRESTException;


}
