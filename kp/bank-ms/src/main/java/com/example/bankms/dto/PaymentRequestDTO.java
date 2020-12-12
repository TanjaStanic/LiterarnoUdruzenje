package com.example.bankms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequestDTO {

    @NotNull
    private String merchantEmail;
    private double amount;
    private long merchantOrderId;
    private ZonedDateTime merchantTimestamp;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
    private String currencyCode;
}
