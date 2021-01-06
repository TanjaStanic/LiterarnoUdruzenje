package com.example.bankms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientCredentials {
    @NotNull
    private String merchantID;
    @NotNull
    private String merchantPassword;
}
