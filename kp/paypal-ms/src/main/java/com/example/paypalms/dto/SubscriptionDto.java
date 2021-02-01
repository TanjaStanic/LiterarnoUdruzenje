package com.example.paypalms.dto;

import com.example.paypalms.domain.Subscription;
import com.example.paypalms.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {

    private Date expirationDate;
    private SubscriptionStatus subscriptionStatus;
    private double paymentAmount;
    private Long merchantOrderId;
    private String currency;

    public SubscriptionDto(Subscription subscription){
        this.expirationDate = subscription.getExpirationDate();
        this.subscriptionStatus = subscription.getSubscriptionStatus();
        this.paymentAmount = subscription.getPaymentAmount();
        this.merchantOrderId = subscription.getMerchantOrderId();
        this.currency = subscription.getCurrency().getCode();
    }
}
