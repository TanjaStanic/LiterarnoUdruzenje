package com.example.paypalms.service;

import com.example.paypalms.domain.Subscription;
import com.example.paypalms.enums.SubscriptionStatus;

import java.util.List;

public interface SubscriptionService extends BaseService<Subscription>{

    List<Subscription> findAllBySellerIdAndSubscriptionStatus(long sellerId, SubscriptionStatus status);

    void syncSubscriptions();
}
