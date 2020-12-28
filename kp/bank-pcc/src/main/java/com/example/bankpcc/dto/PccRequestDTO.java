package com.example.bankpcc.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PccRequestDTO {

	private String cardHolder;
	private String panNumber;
	private String cvv;
	private String mm;
	private String yy;
	private long acquirerOrderId;
	private Date acquirerTimespamp;
}
