package com.example.luservice.dto;


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

    private Long id;
    private Date expirationDate;
    private String subscriptionStatus;
    private double paymentAmount;
    private Long merchantOrderId;
    private String currency;
}
