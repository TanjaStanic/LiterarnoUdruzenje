package com.example.bitcoinms.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
	
	private String id;
    private String status;
    private String price_currency;
    private double price_amount;
    private String receive_currency;
    private double receive_amount;
    private String created_at;
    private String order_id;
    private String payment_url;
    private String token;
	
	

}
