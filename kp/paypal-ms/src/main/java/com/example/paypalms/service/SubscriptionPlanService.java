package com.example.paypalms.service;

import com.example.paypalms.domain.SubscriptionPlan;

import java.util.Collection;

public interface SubscriptionPlanService extends BaseService<SubscriptionPlan> {

    Collection<SubscriptionPlan> findAllBySellerId(Long sellerId);

    Collection<SubscriptionPlan> findAllBySellerEmail(String email);
}
