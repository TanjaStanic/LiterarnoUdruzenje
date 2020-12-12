package com.example.bankms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class BankAcquirerResponseDTO {
    @NotNull
    private String paymentUrl;
    private long paymentId;
}
