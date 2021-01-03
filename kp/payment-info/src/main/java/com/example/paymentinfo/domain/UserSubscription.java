package com.example.paymentinfo.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private long merchantOrderId;
    @ManyToOne
    private Client seller;
    @ManyToOne
    private Currency currency;
    private String successUrl;
    @NotNull
    private String errorUrl;
    @NotNull
    private String failedUrl;
    @NotNull
    private String cancelUrl;
    @Column
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
    @Column
    private Date expirationDate;
}
