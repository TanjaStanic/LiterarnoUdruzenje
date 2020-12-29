package com.example.paypalms.repository;

import com.example.paypalms.domain.Subscription;
import com.example.paypalms.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Collection<Subscription> findAllBySellerIdAndSubscriptionStatus(long sellerId, SubscriptionStatus status);
}
