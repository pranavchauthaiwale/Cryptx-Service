package com.cryptx.models;

public class Portfolio {

	private int portfolioId;
	private int userId;
	private double bitcoin;
	private double litecoin;
	private double ethereum;
	private double walletBalance;

	public int getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getBitcoin() {
		return bitcoin;
	}

	public void setBitcoin(double bitcoin) {
		this.bitcoin = bitcoin;
	}

	public double getLitecoin() {
		return litecoin;
	}

	public void setLitecoin(double litecoin) {
		this.litecoin = litecoin;
	}

	public double getEthereum() {
		return ethereum;
	}

	public void setEthereum(double ethereum) {
		this.ethereum = ethereum;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}
	
	public static Portfolio getDummyPortfolio() {
		Portfolio dummyuserPortfolio = new Portfolio();
		dummyuserPortfolio.setPortfolioId(1);
		dummyuserPortfolio.setUserId(1);;
		dummyuserPortfolio.setBitcoin(0.4);
		dummyuserPortfolio.setLitecoin(2.3);
		dummyuserPortfolio.setEthereum(3.5);
		return dummyuserPortfolio;
	}

}
