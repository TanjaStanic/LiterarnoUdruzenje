package com.example.luservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingDataDto {

    private String recipient;
    private String address;
    private String city;
    private String country;
    private String postalCode;

}
