package com.example.bitcoinms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.util.Assert;
import com.example.bitcoinms.enums.*;

import lombok.Data;
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
	    @Column
	    private Long order_id;      // id bitcoin order-a
	    @Column
	    private Long merchantOrderId;
	    @Column
	    private double amount;
	    @ManyToOne
	    private Currency price_currency;
	    @ManyToOne
	    private Currency receive_currency;
	    @Column
	    private String title;
	    @Column
	    private String description;
	    @Column
	    private String callback_url;
	    @Column
	    private String cancel_url;
	    @Column
	    private String success_url;
	    @Column
	    private String token;
	    @Column
	    private TransactionStatus status;
	    @ManyToOne
	    private Client seller;
	    
	    public Transaction(Client seller, Long merchantOrderId, TransactionStatus status, Double amount, Currency currency) {
	        this.init(seller, merchantOrderId, status, amount);
	        this.price_currency = currency;
	    }

	    
	    public Transaction(Long merchantOrderId, Client seller, TransactionStatus status,
                Double amount, Currency price_currency, Currency recive_currency, String success_url, String cancel_url, String callback_url, String token) {
	    	this.merchantOrderId = merchantOrderId;
	    	this.seller = seller;
	    	this.status = status;
	    	this.amount = amount;
	    	this.price_currency = price_currency;
	    	this.receive_currency = recive_currency;
	    	this.success_url = success_url;
	    	this.cancel_url = cancel_url;
	    	this.callback_url = callback_url;
	    	this.token = token;
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
	    
	   
		
		
		
		
		


	

		
	    
	    
	    
	    
	    
	    
	    
	 
