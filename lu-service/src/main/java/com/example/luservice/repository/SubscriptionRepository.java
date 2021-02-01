package com.example.luservice.repository;


import com.example.luservice.model.SubscriptionStatus;
import com.example.luservice.model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    UserSubscription findByOwnerIdAndSubscriptionStatusIn(long userId, List<SubscriptionStatus> statuses);

    UserSubscription findByMerchantOrderId(long merchantOrderId);
}
