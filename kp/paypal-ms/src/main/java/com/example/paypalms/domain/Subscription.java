package com.example.paypalms.domain;

import com.example.paypalms.dto.SubscriptionRequestDto;
import com.example.paypalms.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String uuid;

    @Column
    private Date expirationDate;

    @Column
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    @Column
    private double paymentAmount;

    @ManyToOne
    private Currency currency;

    @Column
    private String successUrl;

    @Column
    private String errorUrl;

    @Column
    private String failedUrl;

    @Column
    private String cancelUrl;

    @Column
    private String billingPlanId;

    @Column
    private String billingAgreementId;

    @ManyToOne
    private Client seller;

    public Subscription(SubscriptionRequestDto subscriptionRequest, Client client, Currency currency){
        this.seller = client;
        this.paymentAmount = subscriptionRequest.getAmount();
        this.currency = currency;
        this.successUrl = subscriptionRequest.getSuccessUrl();
        this.failedUrl = subscriptionRequest.getFailedUrl();
        this.errorUrl = subscriptionRequest.getErrorUrl();
        this.cancelUrl = subscriptionRequest.getCancelUrl();
        this.subscriptionStatus = SubscriptionStatus.INITIATED;
    }
}
