package com.example.paypalms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingPlanDto {
    private long paymentMethodId;
    private long subscriptionPlanId;
    private String sellerEmail;
}