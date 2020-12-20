package com.example.paymentinfo.repository;

import com.example.paymentinfo.domain.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {

    PaymentRequest findByMerchantOrderId(long merchantOrderId);
}
