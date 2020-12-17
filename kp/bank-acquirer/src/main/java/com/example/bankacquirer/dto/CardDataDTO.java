package com.example.bankacquirer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardDataDTO {
	
	private String cardHolder;
	private String panNumber;	
	private String cvv;
	private String mm;
	private String yy;
}
