package com.example.bankms.dto;

import com.example.bankms.enums.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {

    @NotNull
    private String sellerEmail;
    private TransactionStatus status;
    private long paymentID;
    private Double amount;
    private String currencyCode;
}
