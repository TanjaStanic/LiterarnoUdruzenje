package com.example.paymentinfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponseDTO {
	
	private long paymentId;
	private String paymentUrl;
	
	
	public PaymentResponseDTO(long paymentId, String paymentUrl) {
		super();
		this.paymentId = paymentId;
		this.paymentUrl = paymentUrl;
	}


}
