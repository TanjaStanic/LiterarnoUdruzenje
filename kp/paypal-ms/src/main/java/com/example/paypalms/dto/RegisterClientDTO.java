package com.example.paypalms.dto;

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
    private String clientId;
    @NotNull
    private String clientSecret;
    @NotNull
    private String pcClientId;
}
