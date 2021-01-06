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
public class RegisterClientDTO {
    @NotNull
    private String email;
    @NotNull
    private String merchantID;
    @NotNull
    private String merchantPassword;
    @NotNull
    private String pcClientId;
    @NotNull
    private String passwordConfirm;
}
