package com.example.paymentinfo.dto;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
	private String paymentUrl;

}
