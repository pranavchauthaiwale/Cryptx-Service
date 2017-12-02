package com.cryptx.models;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

	private int transactionId;
	private int userId;
	private int portfolioId;
	private String type;
	private String currency;
	private double numberOfCoins;
	private double transactionAmount;
	private long transactionTime;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

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

	public long getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(long transactionTime) {
		this.transactionTime = transactionTime;
	}
	
	public static List<Transaction> getDummyTransactions(){
		List<Transaction> dummyTransactionsList = new ArrayList<Transaction>();
		
		Transaction transactionOne = new Transaction();
		transactionOne.setTransactionId(1);
		transactionOne.setUserId(1);
		transactionOne.setPortfolioId(1);
		transactionOne.setType("BUY");
		transactionOne.setCurrency("Bitcoin");
		transactionOne.setNumberOfCoins(0.01);
		transactionOne.setTransactionAmount(35.51);
		transactionOne.setTransactionTime(1510068138);
		
		Transaction transactionTwo = new Transaction();
		transactionTwo.setTransactionId(2);
		transactionTwo.setUserId(1);
		transactionTwo.setPortfolioId(1);
		transactionTwo.setType("BUY");
		transactionTwo.setCurrency("Bitcoin");
		transactionTwo.setNumberOfCoins(0.01);
		transactionTwo.setTransactionAmount(35.51);
		transactionTwo.setTransactionTime(1510672938);
		
		Transaction transactionThree = new Transaction();
		transactionThree.setTransactionId(3);
		transactionThree.setUserId(1);
		transactionThree.setPortfolioId(1);
		transactionThree.setType("BUY");
		transactionThree.setCurrency("Bitcoin");
		transactionThree.setNumberOfCoins(0.01);
		transactionThree.setTransactionAmount(35.51);
		transactionThree.setTransactionTime(1511277738);
		
		Transaction transactionFour = new Transaction();
		transactionFour.setTransactionId(4);
		transactionFour.setUserId(1);
		transactionFour.setPortfolioId(1);
		transactionFour.setType("BUY");
		transactionFour.setCurrency("Bitcoin");
		transactionFour.setNumberOfCoins(0.01);
		transactionFour.setTransactionAmount(35.51);
		transactionFour.setTransactionTime(1511882538);
		
		dummyTransactionsList.add(transactionOne);
		dummyTransactionsList.add(transactionTwo);
		dummyTransactionsList.add(transactionThree);
		dummyTransactionsList.add(transactionFour);
		return dummyTransactionsList;
	}

}
