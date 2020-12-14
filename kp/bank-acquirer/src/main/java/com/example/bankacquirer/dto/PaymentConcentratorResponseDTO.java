package com.example.bankacquirer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentConcentratorResponseDTO {
	
	private long paymentId;
	private String paymentUrl;
	
	
	public PaymentConcentratorResponseDTO(long paymentId, String paymentUrl) {
		super();
		this.paymentUrl = paymentUrl;
		this.paymentId = paymentId;
	}
	
	
}
