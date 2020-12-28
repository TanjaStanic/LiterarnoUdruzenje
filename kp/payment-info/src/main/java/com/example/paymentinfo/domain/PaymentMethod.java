package com.example.paymentinfo.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentMethod {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @Column
    private String name;
    
    @Column
    private boolean subscriptionSupported;

    @Column
    private String applicationName;

    @ManyToMany
    private Set<Client> clients = new HashSet<>();

}
