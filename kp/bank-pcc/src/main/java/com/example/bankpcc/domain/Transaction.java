package com.example.bankpcc.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Transaction {

	@Id
    private Long id;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	
	@Column(nullable = false)
	private String cardHolder;
	
	@Column(nullable = false)
	private double amount;
	
	@Column(nullable = false)
	private long acquirerOrderId;
	
	@Column(nullable = false)
	private Date acquirerTimestamp;
	
	@Column
	private long issuerOrderId;
	
	@Column
	private Date issuerTimestamp;
	
	@Column
	private Boolean isAuthentificated;
	
	@Column
	private Boolean isAutorized;
	
	
	
}
