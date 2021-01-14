package com.example.bitcoinms.dto;

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

    private String token;
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String pcClientId;
}
