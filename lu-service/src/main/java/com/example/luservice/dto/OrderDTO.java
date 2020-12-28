package com.example.luservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
	@NotNull
    private List<OrderItemDto> orderItems;
    private double amount;
    private ShippingDataDto shippingInfo;

}
