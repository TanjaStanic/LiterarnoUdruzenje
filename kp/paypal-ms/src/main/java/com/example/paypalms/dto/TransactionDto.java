package com.example.paypalms.dto;

import com.example.paypalms.domain.Transaction;
import com.example.paypalms.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public TransactionDto(Transaction transaction){
        this.sellerEmail = transaction.getClient().getEmail();
        this.status = transaction.getStatus();
        this.merchantOrderId = transaction.getMerchantOrderId();
        this.paymentID = transaction.getPaymentId();
        this.amount = transaction.getAmount();
        this.currencyCode = transaction.getCurrency().getCode();
    }


}
