package com.example.bankacquirer.dto;

import com.example.bankacquirer.domain.Transaction;
import com.example.bankacquirer.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
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

    public TransactionDto(Transaction transaction) {
        this.amount = transaction.getAmount();
        if (transaction.getClient() != null){
            this.sellerEmail = transaction.getClient().getEmail();
        }
        this.status = transaction.getStatus();
        this.merchantOrderId = transaction.getMerchantOrderId();
        this.paymentID = transaction.getPaymentID();
        if (transaction.getCurrency() != null){
            this.currencyCode = transaction.getCurrency().getCode();
        }
    }
}
