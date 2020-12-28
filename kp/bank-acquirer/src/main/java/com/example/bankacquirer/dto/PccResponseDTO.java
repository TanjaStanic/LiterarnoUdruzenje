package com.example.bankacquirer.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PccResponseDTO {

	private Boolean isAuthentificated;
	private Boolean isAutorized;
	private long acquirerOrederId;
	private Date acquirerTimestamp;
	private long issuerOrderId;
	private Date issuerTimespamp;
	
}
