package com.example.paymentinfo.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
    private String cancelUrl;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Transaction(Client seller, TransactionStatus status, Long merchantOrderId, Double amount, Currency currency) {
        this.seller = seller;
        this.status = status;
        this.merchantOrderId = merchantOrderId;
        this.amount = amount;
        this.currency = currency;
        this.created = new Date();
    }


}
