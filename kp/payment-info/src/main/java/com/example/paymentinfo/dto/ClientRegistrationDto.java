package com.example.paymentinfo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientRegistrationDto {
    @NotNull
    private String taxIdentificationNumber; // pib
    @NotNull
    private String companyRegistrationNumber; // maticni broj
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String passwordConfirm;
}
