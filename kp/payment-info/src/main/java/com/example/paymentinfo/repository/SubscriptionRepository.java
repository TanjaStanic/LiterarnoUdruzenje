package com.example.paymentinfo.repository;

import com.example.paymentinfo.domain.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    UserSubscription findByMerchantOrderId(long merchantOrderId);
}
