package com.example.paymentinfo.dto;

import com.example.paymentinfo.domain.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscriptionDto {
    private String seller;
    private long merchantOrderId;
    private SubscriptionStatus status;
    private Date expirationDate;

}
