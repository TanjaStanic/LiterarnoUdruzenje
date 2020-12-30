package com.example.paypalms.dto;

import com.example.paypalms.domain.SubscriptionPlan;
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

    public SubscriptionPlanDto(SubscriptionPlan subscriptionPlan){
        this.id = subscriptionPlan.getId();
        this.cyclesNumber = subscriptionPlan.getCyclesNumber();
        this.subscriptionFrequency = subscriptionPlan.getFrequency().getName();
        this.subscriptionType = subscriptionPlan.getType().getName();
    }

}
