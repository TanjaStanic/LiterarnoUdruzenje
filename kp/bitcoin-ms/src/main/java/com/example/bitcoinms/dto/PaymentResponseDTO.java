package com.example.bitcoinms.dto;

import com.example.bitcoinms.domain.Currency;

import lombok.Data;

@Data
public class PaymentResponseDTO {
	
	private String id;
    private String status;
    private Currency price_currency;
    private double price_amount;
    private Currency receive_currency;
    private double receive_amount;
    private String created_at;
    private String order_id;
    private String payment_url;
    private String token;
	
	

}
