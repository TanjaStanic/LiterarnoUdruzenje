package com.example.luservice.dto;


import com.example.luservice.model.SubscriptionStatus;
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
}
