package com.example.paymentinfo.service;

import com.example.paymentinfo.domain.PaymentMethod;

import java.util.Collection;

public interface PaymentMethodService extends BaseService<PaymentMethod>{
    Collection<PaymentMethod> findAllBySubscriptionSupportedIsTrueAndClientsEmail(String email);
}
