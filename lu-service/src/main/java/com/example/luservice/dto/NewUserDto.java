package com.example.luservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewUserDto {
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @NotNull
    private String password;
    @NotNull
    private String passwordConfirm;

    @Override
    public String toString() {
        return "NewUserDto{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }
}
