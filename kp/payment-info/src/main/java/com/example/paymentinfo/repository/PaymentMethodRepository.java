package com.example.paymentinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.paymentinfo.domain.PaymentMethod;

import java.util.Collection;
import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    Collection<PaymentMethod> findAllBySubscriptionSupportedIsTrueAndClientsEmail(String email);

    PaymentMethod findByName(String name);
    
    PaymentMethod findOneById(Long id);

    PaymentMethod findByApplicationName(String name);
    
    List<PaymentMethod> findAllByClients_id(Long id);

}
