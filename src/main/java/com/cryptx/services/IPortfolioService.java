package com.cryptx.services;

import java.util.List;

import com.cryptx.exception.CryptxException;
import com.cryptx.exception.EmptyDataException;
import com.cryptx.models.Portfolio;

public interface IPortfolioService {
	
	public static final String PORTFOLIO_SERVICE = "PortfolioService";

	Portfolio getUserPortfolio(int userId) throws CryptxException;
	
	double getAmount(String cryptoCurrency, int userId) throws CryptxException;
	
	void createUserPortfolio(int userId) throws CryptxException;
	
	void updatePortfolio(String cryptoCurrency, double amount, int userId) throws CryptxException;
	
	List<Double[]> getPortfolioBitcoinTrend(int userId, int portfolioId) throws CryptxException, EmptyDataException ;
	
	List<Double[]> getPortfolioLitecoinTrend(int userId, int portfolioId) throws CryptxException, EmptyDataException ;
	
	List<Double[]> getPortfolioEthereumTrend(int userId, int portfolioId) throws CryptxException, EmptyDataException ;
	
	List<Double[]> getPortfolioVirtualWalletTrend(int userId, int portfolioId) throws CryptxException, EmptyDataException ;
}
