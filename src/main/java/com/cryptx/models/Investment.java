package com.cryptx.models;

public class Investment {
	private String currency;
	private double amount;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return String.format("Currency: [%s], Amount: [%s]", this.currency, Double.toString(this.amount));
	}
}
