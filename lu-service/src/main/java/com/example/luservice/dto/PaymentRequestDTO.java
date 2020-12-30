package com.example.luservice.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequestDTO {

	private String merchantEmail;
    private double amount;
    private long merchantOrderId;
    private Date merchantTimestamp;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
    private String currencyCode;
    private String cancelUrl;

}
