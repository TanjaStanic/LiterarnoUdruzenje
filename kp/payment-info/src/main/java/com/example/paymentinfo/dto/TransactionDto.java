package com.example.paymentinfo.dto;

import com.example.paymentinfo.domain.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
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
