package com.example.bitcoinms.domain;

import javax.persistence.*;

import lombok.*;
import org.springframework.util.Assert;
import com.example.bitcoinms.enums.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String order_id;      // id bitcoin order-a
    @Column
    private Long merchantOrderId;
    @Column
    private double amount;
    @ManyToOne
    private Currency price_currency;
    @ManyToOne
    private Currency receive_currency;
    @Column
    private String callback_url;
    @Column
    private String cancel_url;
    @Column
    private String success_url;
    @Column
    private String error_url;
    @Column
    private String paymentId;
    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @ManyToOne
    private Client seller;

    public Transaction(Client seller, Long merchantOrderId, TransactionStatus status, Double amount, Currency currency) {
        this.init(seller, merchantOrderId, status, amount);
        this.price_currency = currency;
    }


    public Transaction(Long merchantOrderId, Client seller, TransactionStatus status,
                       Double amount, Currency price_currency, Currency recive_currency, String success_url, String cancel_url, String callback_url) {
        this.merchantOrderId = merchantOrderId;
        this.seller = seller;
        this.status = status;
        this.amount = amount;
        this.price_currency = price_currency;
        this.receive_currency = recive_currency;
        this.success_url = success_url;
        this.cancel_url = cancel_url;
        this.callback_url = callback_url;
    }

    private void init(Client seller, Long merchantOrderId, TransactionStatus status, Double amount) {
        Assert.notNull(seller, "Attribute seller cannot be null.");
        Assert.notNull(status, "Attribute status cannot be null.");
        Assert.notNull(merchantOrderId, "Attribute merchantOrderId cannot be null.");
        Assert.notNull(amount, "Attribute amount cannot be null.");
        this.seller = seller;
        this.merchantOrderId = merchantOrderId;
        this.status = status;
        this.amount = amount;

    }


}
	    
	   
		
		
		
		
		


	

		
	    
	    
	    
	    
	    
	    
	    
	 
