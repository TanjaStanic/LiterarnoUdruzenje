package com.example.bankms.domain;

import com.example.bankms.enums.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.ZonedDateTime;

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
    private long paymentID;

    @Column
    private Long merchantOrderId;

    @Column
    private Long acquirerOrderId;

    @Column
    private ZonedDateTime acquirerTimestamp;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column
    private Double amount;

    @ManyToOne
    private Currency currency;

    public Transaction(Client seller, Long merchantOrderId, TransactionStatus status, Double amount, Currency currency) {
        this.init(seller, merchantOrderId, status, amount);
        this.currency = currency;
    }


    private void init(Client seller, Long merchantOrderId, TransactionStatus status, Double amount) {
        Assert.notNull(seller, "Attribute seller cannot be null.");
        Assert.notNull(status, "Attribute status cannot be null.");
        Assert.notNull(merchantOrderId, "Attribute merchantOrderId cannot be null.");
        Assert.notNull(amount, "Attribute amount cannot be null.");
        this.seller = seller;
        this.merchantOrderId = merchantOrderId;
        this.status = status;
        this.amount = amount;

    }
}
