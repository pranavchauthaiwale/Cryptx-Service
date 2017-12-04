package com.cryptx.views;

public class VirtualWalletView {
	private int cardId;
	private int portfolioId;
	private double amount;

	public int getPaymentMethodId() {
		return cardId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.cardId = paymentMethodId;
	}

	public int getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
