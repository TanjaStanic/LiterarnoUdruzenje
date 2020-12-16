package com.example.bankms.dto;


import com.example.bankms.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompletedPaymentDTO {
    @NotNull
    private TransactionStatus transactionStatus;
    @NotNull
    private Long merchantOrderID;
    @NotNull
    private Long acquirerOrderID;
    @NotNull
    private ZonedDateTime acquirerTimestamp;
    @NotNull
    private long paymentID;
    @NotNull
    private String url;
}
