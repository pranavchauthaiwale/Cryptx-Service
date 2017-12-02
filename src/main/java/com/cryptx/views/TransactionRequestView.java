package com.cryptx.views;

public class TransactionRequestView {
	private int portfolioId;
	private String type;
	private String currency;
	private double numberOfCoins;
	private double transactionAmount;
	private double transactionTime;

	public int getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getNumberOfCoins() {
		return numberOfCoins;
	}

	public void setNumberOfCoins(double numberOfCoins) {
		this.numberOfCoins = numberOfCoins;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public double getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(double transactionTime) {
		this.transactionTime = transactionTime;
	}

}
