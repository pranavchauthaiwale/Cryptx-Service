package com.cryptx.models;

import java.util.ArrayList;
import java.util.List;

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
	
	public static List<Investment> getDummyInvestments() {
		List<Investment> dummyInvestmentList = new ArrayList<Investment>();
		Investment investmentOne = new Investment();
		investmentOne.setCurrency("Bitcoin");
		investmentOne.setAmount(0.4);
		
		Investment investmentTwo = new Investment();
		investmentTwo.setCurrency("Litecoin");
		investmentTwo.setAmount(2.3);
		
		Investment investmentThree = new Investment();
		investmentThree.setCurrency("Ethereum");
		investmentThree.setAmount(3.5);
		
		Investment investmentFour = new Investment();
		investmentFour.setCurrency("Ripple");
		investmentFour.setAmount(0);
		
		dummyInvestmentList.add(investmentOne);
		dummyInvestmentList.add(investmentTwo);
		dummyInvestmentList.add(investmentThree);
		dummyInvestmentList.add(investmentFour);
		
		return dummyInvestmentList;
	}
}
