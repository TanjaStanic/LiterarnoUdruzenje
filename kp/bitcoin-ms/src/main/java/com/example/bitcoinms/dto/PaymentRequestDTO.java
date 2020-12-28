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

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequestDTO {
		
	    @NotNull
	    @Email
	    private String merchantEmail;

	    @NotNull
	    private Long merchantOrderId;
	    
	    @NotNull
	    private Long orderId;
	    
	    @NotNull
	    @Positive
	    private double amount;
	    
	    @NotNull
	    private String currencyCode;
	    
	    @NotNull
	    private String callbackUrl;
	    
	    @NotNull
	    private String cancelUrl;
	    
	    @NotNull
	    private String successUrl;
	    
	    @NotNull
	    private String token;
	    
	    
}
