package com.example.paymentinfo.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client seller;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(unique = true)
    private Long merchantOrderId;

    @Column
    private String paymentID;

    @Column
    private Double amount;

    @ManyToOne
    private Currency currency;

    public Transaction(Client seller, TransactionStatus status, Long merchantOrderId, String paymentID, Double amount, Currency currency) {
        this.seller = seller;
        this.status = status;
        this.merchantOrderId = merchantOrderId;
        this.paymentID = paymentID;
        this.amount = amount;
        this.currency = currency;
    }
}
