package com.example.paypalms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SubscriptionType type;

    @ManyToOne
    private SubscriptionFrequency frequency;

    @Column
    private Integer cyclesNumber;

    @ManyToOne
    private Client seller;
}
