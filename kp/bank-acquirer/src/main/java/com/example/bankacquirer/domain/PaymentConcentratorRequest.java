package com.example.bankacquirer.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentConcentratorRequest {
	
	@Id
	private long id;
	
	@Column(nullable = false)
	private double amount;
	
	@Column(nullable = false)
    private String merchantId;
	
	@Column(nullable = false)
    private String merchantPassword;
	
	@Column(nullable = false)
    private long merchantOrderId;
	
	@Column(nullable = false)
    private Date merchantTimestamp;
	
	@Column(nullable = false)
    private String successUrl;
	
	@Column(nullable = false)
    private String failedUrl;
	
    private String errorUrl;

	public PaymentConcentratorRequest(double amount, String merchantId, String merchantPassword, long merchantOrderId,
			Date merchantTimestamp, String successUrl, String failedUrl, String errorUrl) {
		super();
		this.amount = amount;
		this.merchantId = merchantId;
		this.merchantPassword = merchantPassword;
		this.merchantOrderId = merchantOrderId;
		this.merchantTimestamp = merchantTimestamp;
		this.successUrl = successUrl;
		this.failedUrl = failedUrl;
		this.errorUrl = errorUrl;
	}
    
    
    
}
