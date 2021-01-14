package com.example.paymentinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paymentinfo.domain.PaymentMethod;

import java.util.Collection;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    Collection<PaymentMethod> findAllBySubscriptionSupportedIsTrueAndClientsEmail(String email);

    PaymentMethod findByName(String name);

    PaymentMethod findByApplicationName(String name);
}
