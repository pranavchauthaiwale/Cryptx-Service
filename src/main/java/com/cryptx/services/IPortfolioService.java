package com.cryptx.services;

import com.cryptx.exception.CryptxException;
import com.cryptx.models.Portfolio;

public interface IPortfolioService {
	
	public static final String PORTFOLIO_SERVICE = "PortfolioService";

	Portfolio getUserPortfolio(int userId) throws CryptxException;
	
	double getAmount(String cryptoCurrency, int userId) throws CryptxException;
	
	void createUserPortfolio(int userId) throws CryptxException;
	
	void updatePortfolio(String cryptoCurrency, double amount, int userId) throws CryptxException;
}
