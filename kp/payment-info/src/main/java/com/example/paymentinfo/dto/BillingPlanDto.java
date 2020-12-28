package com.example.paymentinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingPlanDto {

    private String paymentMethod;
    private long subscriptionPlan;
    private String sellerEmail;
}
