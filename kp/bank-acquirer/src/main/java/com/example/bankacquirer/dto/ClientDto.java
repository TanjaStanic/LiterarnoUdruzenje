package com.example.bankacquirer.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    @NotNull
    private String merchantID;
    @NotNull
    private String merchantPassword;
    @NotNull
    private String email;
    @NotNull
    private String name;
}
