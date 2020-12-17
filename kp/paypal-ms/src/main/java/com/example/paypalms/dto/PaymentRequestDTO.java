package com.example.paypalms.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequestDTO {
    @NotNull
    @Email
    private String sellerEmail;

    @NotNull
    private Long merchantOrderId;

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    private String currency;

    @NotNull
    private String successUrl;

    @NotNull
    private String errorUrl;

    @NotNull
    private String failedUrl;
}
