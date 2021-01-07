package com.example.bankacquirer.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentConcentratorRequestDTO {

	private double amount;
    private String merchantId;
    private String merchantPassword;
    private long merchantOrderId;
    private Date merchantTimestamp;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
    private String currencyCode;
}
