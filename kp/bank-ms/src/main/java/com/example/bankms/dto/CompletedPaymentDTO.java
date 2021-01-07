package com.example.bankms.dto;


import com.example.bankms.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompletedPaymentDTO {
    @NotNull
    private TransactionStatus transactionStatus;
    @NotNull
    private Long merchantOrderID;

    private Long acquirerOrderID;

    private ZonedDateTime acquirerTimestamp;
    @NotNull
    private long paymentID;

    private Long issuerOrderID;

    private Date issuerTimestamp;

}
