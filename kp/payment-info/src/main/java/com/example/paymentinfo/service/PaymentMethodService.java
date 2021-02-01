package com.example.paymentinfo.service;

import com.example.paymentinfo.domain.PaymentMethod;

import java.util.Collection;
import java.util.List;

public interface PaymentMethodService extends BaseService<PaymentMethod>{
    Collection<PaymentMethod> findAllBySubscriptionSupportedIsTrueAndClientsEmail(String email);
    void deleteOneFromUser(Long userId, Long paymentMethodId);
    List<PaymentMethod> findAllNoSupportedByClient(Long id);
    void addPaymetMethodToClient(Long userId, Long paymentMethodId);
}
