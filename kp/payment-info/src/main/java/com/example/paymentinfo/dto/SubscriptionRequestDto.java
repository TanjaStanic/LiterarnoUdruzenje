package com.example.paymentinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequestDto {
    private long subscriptionPlan;
    @NotNull
    private String sellerEmail;
    private double amount;
    @NotNull
    private String currencyCode;
    @NotNull
    private String successUrl;
    @NotNull
    private String errorUrl;
    @NotNull
    private String failedUrl;
    @NotNull
    private String cancelUrl;
}
