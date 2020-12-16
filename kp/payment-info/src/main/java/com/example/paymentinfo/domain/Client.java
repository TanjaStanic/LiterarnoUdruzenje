package com.example.paymentinfo.domain;

import java.security.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String taxIdentificationNumber; // pib
	
	@Column
	private String companyRegistrationNumber; // maticni broj
	
	@Column
	private String email;
	
	@Column
	private String name;
	
	@Column
	private String password;		// hesirati
    
    @Column
    private boolean active;
    
	@Column
    private boolean enabled;
	
    @Column(name = "lastPasswordResetDate")
	private Timestamp lastPasswordResetDate;
    
    @ManyToMany
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

}
