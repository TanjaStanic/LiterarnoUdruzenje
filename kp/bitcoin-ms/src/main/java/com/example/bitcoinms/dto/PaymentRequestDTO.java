package com.example.bitcoinms.dto;


import javax.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequestDTO {
    @NotNull
    private String merchantEmail;
    private Long merchantOrderId;
    private Long orderId;
    private double amount;
    private String currencyCode;
    private String callbackUrl;
    private String cancelUrl;
    private String successUrl;
    private String errorUrl;
    private String failedUrl;
}
