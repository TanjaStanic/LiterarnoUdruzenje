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
public class SubscriptionPlanDto {
    @NotNull
    private Long id;
    private int cyclesNumber;
    @NotNull
    private String subscriptionType;
    @NotNull
    private String subscriptionFrequency;


}