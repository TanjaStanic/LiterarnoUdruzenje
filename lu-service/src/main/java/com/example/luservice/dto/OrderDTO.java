package com.example.luservice.dto;

import com.example.luservice.model.Currency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
	
    private Long orderId;
    private String hashedOrderId;
    private String item;
    private double priceAmount;
    private Currency priceCurrency;
    private String title;
    private String description;

}
