package com.example.bankacquirer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
	private Bank bank;
	
    @Column
    private String accountNumber;

    @Column(nullable = false)
	private double availableFunds;

    @OneToOne
    private Client owner;
	
	@ManyToOne
	private Currency currency;
	
	@OneToMany
    private Set<Card> cards = new HashSet<>();
}
