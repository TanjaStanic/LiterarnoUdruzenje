package com.example.paymentinfo.domain;

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
public class PaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private long merchantOrderId;
    @Column
    private String merchantEmail;
    @Column
    private double amount;
    @Column
    private Date merchantTimestamp;
    @Column
    private String successUrl;
    @Column
    private String failedUrl;
    @Column
    private String errorUrl;
    @Column
    private String currencyCode;
    private String callbackUrl;
    private String cancelUrl;
}
