package com.example.bankacquirer.dto;

import java.time.ZonedDateTime;
import java.util.Date;

import com.example.bankacquirer.domain.TransactionStatus;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
