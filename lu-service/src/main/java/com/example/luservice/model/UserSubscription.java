package com.example.luservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscription {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date expirationDate;

    @Column
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    @Column
    private double paymentAmount;

    @Column
    private Long merchantOrderId;


    private String currency;

    @OneToOne
    private User owner;

}
