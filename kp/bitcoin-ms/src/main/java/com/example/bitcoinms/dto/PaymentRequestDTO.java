package com.example.bitcoinms.dto;

import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.example.bitcoinms.domain.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequestDTO {
		
	    @NotNull
	    private String merchantEmail;
	    private Long merchantOrderId;
	    private Long orderId;
	    private double amount;
	    private String currencyCode;
	    private String callbackUrl;
	    private String cancelUrl;	    
	    private String successUrl;
	    private String token;
	    
	    
}
