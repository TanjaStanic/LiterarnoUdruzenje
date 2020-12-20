package com.example.paymentinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.paymentinfo.domain.PaymentMethod;

import java.util.Collection;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {


}
