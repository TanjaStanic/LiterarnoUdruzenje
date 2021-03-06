package com.example.bitcoinms.dto;

public class CoinGateOrderDTO {
	
	private double price_amount;
    private String price_currency;
    private String receive_currency;
    private String title;
    private String description;
    
	public CoinGateOrderDTO() {
		
	}
	public CoinGateOrderDTO(double price_amount, String price_currency, String receive_currency, String title,
			String description) {
		super();
		this.price_amount = price_amount;
		this.price_currency = price_currency;
		this.receive_currency = receive_currency;
		this.title = title;
		this.description = description;
	}
	public double getPrice_amount() {
		return price_amount;
	}
	public void setPrice_amount(double price_amount) {
		this.price_amount = price_amount;
	}
	public String getPrice_currency() {
		return price_currency;
	}
	public void setPrice_currency(String price_currency) {
		this.price_currency = price_currency;
	}
	public String getReceive_currency() {
		return receive_currency;
	}
	public void setReceive_currency(String receive_currency) {
		this.receive_currency = receive_currency;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
    

}
