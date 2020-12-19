package com.example.luservice.model;

import java.time.ZonedDateTime;

import javax.persistence.*;

import org.springframework.util.Assert;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Data
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Client client;

    @Column
	private long paymentID;

	@Column
	private Long merchantOrderId;

	@Column
    private Long acquirerOrderId;

    @Column
	private ZonedDateTime acquirerTimestamp;

	@Column
    @Enumerated(EnumType.STRING)
	private TransactionStatus status;

	@Column
    private Double amount;

	@ManyToOne
	private Currency currency;

}
