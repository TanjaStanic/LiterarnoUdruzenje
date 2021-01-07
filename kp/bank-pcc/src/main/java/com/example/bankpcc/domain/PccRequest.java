package com.example.bankpcc.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.bankpcc.dto.PccRequestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PccRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String cardHolder;
	
	@Column(nullable = false)
	private String panNumber;
	
	@Column(nullable = false)
	private String cvv;
	
	@Column(nullable = false)
	private String mm;
	
	@Column(nullable = false)
	private String yy;
	
	@Column(nullable = false)
	private double amount;
	
	@Column(nullable = false)
	private long acquirerOrderId;
	
	@Column(nullable = false)
	private Date acquirerTimestamp;

	public PccRequest(PccRequestDTO pccRequestDTO) {
		super();
		this.cardHolder = pccRequestDTO.getCardHolder();
		this.panNumber = pccRequestDTO.getPanNumber();
		this.cvv = pccRequestDTO.getCvv();
		this.mm = pccRequestDTO.getMm();
		this.yy = pccRequestDTO.getYy();
		this.amount = pccRequestDTO.getAmount();
		this.acquirerOrderId = pccRequestDTO.getAcquirerOrderId();
		this.acquirerTimestamp = pccRequestDTO.getAcquirerTimestamp();
	}

}
