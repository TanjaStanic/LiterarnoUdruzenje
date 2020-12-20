package com.example.bankms.dto;

import com.example.bankms.enums.TransactionStatus;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TransactionDto {

    @NotNull
    private String sellerEmail;
    private TransactionStatus status;
    @NotNull
    private Long merchantOrderId;
    private String paymentID;
    private Double amount;
    private String currencyCode;
}
