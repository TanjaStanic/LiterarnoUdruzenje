package com.example.paypalms.domain;

import com.example.paypalms.enums.TransactionStatus;
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
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long merchantOrderId;

    @ManyToOne
    private Client client;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column
    private Double amount;

    @ManyToOne
    private Currency currency;

    @Column
    private String paymentId;

    @Column
    private String successUrl;

    @Column
    private String errorUrl;

    @Column
    private String failedUrl;

    public Transaction(Long merchantOrderId, Client client, Date date, TransactionStatus status,
                       Double amount, Currency currency, String successUrl, String errorUrl, String failedUrl) {
        this.merchantOrderId = merchantOrderId;
        this.client = client;
        this.date = date;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.successUrl = successUrl;
        this.errorUrl = errorUrl;
        this.failedUrl = failedUrl;
    }
}
