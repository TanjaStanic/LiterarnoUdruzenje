package com.example.luservice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class PaymentMethod {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column
     private String name;
	
	 @Column
     private String createTransactionURI;	
	 @Column	
	 private String checkStatusURI;
	
	 @Column
	 private boolean isBank;
	 
	 @Column
	 private boolean isPayPal;
	 
	 @Column
	 private boolean isBitcoin;
	 
	 @OneToMany
	 private List<FormData> requiredFormData;
	 	 
	
}
