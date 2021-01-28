package com.example.luservice.dto;

import com.example.luservice.model.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {

    @NotNull
    private String sellerEmail;
    private TransactionStatus status;
    @NotNull
    private Long merchantOrderId;
    private String paymentID;
    private Double amount;
    private String currencyCode;
}
