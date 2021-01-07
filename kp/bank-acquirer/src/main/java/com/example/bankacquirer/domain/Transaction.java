package com.example.bankacquirer.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
	
	@Id
	private Long id;
	
	@ManyToOne
	private Client client;

	@Column
	private String paymentID;

	@Column
	private Long merchantOrderId;

	@Column
	private Long acquirerOrderId;

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
