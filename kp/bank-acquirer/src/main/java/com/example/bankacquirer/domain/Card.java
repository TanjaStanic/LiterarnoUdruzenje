package com.example.bankacquirer.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="card")
@Getter
@Setter
@NoArgsConstructor
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String pan;

	@Column(nullable = false)
	private String cvv;

	@Column(nullable = false)
	private String expirationDate;
	
	@ManyToOne
	private Account account;
}
