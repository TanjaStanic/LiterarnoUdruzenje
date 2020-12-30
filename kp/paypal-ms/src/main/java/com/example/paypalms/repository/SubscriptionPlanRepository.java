package com.example.paypalms.repository;


import com.example.paypalms.domain.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    Collection<SubscriptionPlan> findAllBySellerId(Long sellerId);

    Collection<SubscriptionPlan> findAllBySellerEmail(String email);



}
