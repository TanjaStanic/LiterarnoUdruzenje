package com.example.luservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {
	
	 @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long id;

	    @Column
	    private String merchantID;

	    @Column
	    private String merchantPassword; 
	    @Column
	    private String email;

	    @Column
	    private String name;
	    
	    @ManyToMany
	    private List<PaymentMethod> paymentMethods;

	    @Column
	    private Long userId;

	    @OneToMany
	    private List<PaymentData> paymentsData;
}
