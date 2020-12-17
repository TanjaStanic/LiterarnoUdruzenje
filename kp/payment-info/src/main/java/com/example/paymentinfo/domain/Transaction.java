package com.example.paymentinfo.domain;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Client seller;

	@OneToMany
    private Set<ClientPaymentInformation> paymentInfo = new HashSet<>();

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date acquirerTimestamp;

	@Column
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	@Column
	private Double amount;
	
	@ManyToOne
	private Currency currency;

}
