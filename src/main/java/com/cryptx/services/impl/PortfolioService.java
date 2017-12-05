package com.cryptx.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptx.dao.IDataAccess;
import com.cryptx.exception.CryptxException;
import com.cryptx.exception.EmptyDataException;
import com.cryptx.models.Portfolio;
import com.cryptx.services.IPortfolioService;

@Service(IPortfolioService.PORTFOLIO_SERVICE)
public class PortfolioService implements IPortfolioService {

	@Autowired
	IDataAccess dataAccess;

	@Override
	public Portfolio getUserPortfolio(int userId) throws CryptxException {

		String query = String.format(
				"SELECT userid, portfolioid, amount, bitcoin, ethereum, litecoin FROM portfolio WHERE userid = %d",
				userId);

		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				createUserPortfolio(userId);
				return getUserPortfolio(userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildPortfolioFromResultSet(rs);
	}

	// @Override
	// public Portfolio updatePortfolioAmount(double amount, int userId) throws
	// CryptxException {
	//
	// Portfolio userPortfolio = getUserPortfolio(userId);
	//
	// if (userPortfolio == null) {
	// createUserPortfolio(userId);
	// }
	//
	// String query = String.format("UPDATE wallet SET amount = %f WHERE userid =
	// %d", amount,
	// userId);
	//
	// try {
	// dataAccess.executeQuery(query);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// throw new CryptxException("Error in Updating Virtual Amount in Portfolio");
	// }
	// return getUserPortfolio(userId);
	// }

	private Portfolio buildPortfolioFromResultSet(ResultSet resultSet) throws CryptxException {
		Portfolio userPortfolio = new Portfolio();
		try {
			while (resultSet.next()) {
				userPortfolio.setPortfolioId(resultSet.getInt("portfolioid"));
				userPortfolio.setUserId(resultSet.getInt("userid"));
				userPortfolio.setAmount(resultSet.getDouble("amount"));
				userPortfolio.setBitcoin(resultSet.getDouble("bitcoin"));
				userPortfolio.setEthereum(resultSet.getDouble("ethereum"));
				userPortfolio.setLitecoin(resultSet.getDouble("litecoin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return userPortfolio;
	}

	@Override
	public void createUserPortfolio(int userId) throws CryptxException {
		String query = String.format(
				"INSERT INTO PORTFOLIO (userid, amount, bitcoin, ethereum,litecoin ) VALUES (%d, 0, 0, 0, 0)", userId);
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Creating Portfolio for user");
		}
	}

	@Override
	public void updatePortfolio(String cryptoCurrency, double amount, int userId) throws CryptxException {

		Portfolio userPortfolio = getUserPortfolio(userId);

		if (userPortfolio == null) {
			createUserPortfolio(userId);
			userPortfolio = getUserPortfolio(userId);
		}

		String updateString;

		if (cryptoCurrency.equalsIgnoreCase("Bitcoin")) {
			updateString = String.format("bitcoin = %f", amount);
		} else if (cryptoCurrency.equalsIgnoreCase("Ethereum")) {
			updateString = String.format("ethereum = %f", amount);
		} else if (cryptoCurrency.equalsIgnoreCase("Litecoin")) {
			updateString = String.format("litecoin = %f", amount);
		} else {
			updateString = String.format("amount = %f", amount);
		}

		String query = String.format("UPDATE portfolio SET %s WHERE portfolioid = %d", updateString,
				userPortfolio.getPortfolioId());

		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Updating User Portfolio");
		}
	}

	@Override
	public double getAmount(String cryptoCurrency, int userId) throws CryptxException {

		Portfolio userPortfolio = getUserPortfolio(userId);

		if (userPortfolio == null) {
			createUserPortfolio(userId);
			userPortfolio = getUserPortfolio(userId);
		}

		double amount;

		if (cryptoCurrency.equalsIgnoreCase("Bitcoin")) {
			amount = userPortfolio.getBitcoin();
		} else if (cryptoCurrency.equalsIgnoreCase("Ethereum")) {
			amount = userPortfolio.getEthereum();
		} else if (cryptoCurrency.equalsIgnoreCase("Litecoin")) {
			amount = userPortfolio.getLitecoin();
		} else {
			amount = userPortfolio.getAmount();
		}

		return amount;
	}

	@Override
	public List<Double[]> getPortfolioBitcoinTrend(int userId, int portfolioId) throws CryptxException, EmptyDataException {
		String query = String.format(
				"SELECT timestmp, bitcoin FROM portfolio_hourly WHERE userid = %d AND portfolioid = %d", userId,
				portfolioId);
		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new EmptyDataException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildPortfolioTrendFromResultSet(rs, "bitcoin");
	}

	@Override
	public List<Double[]> getPortfolioLitecoinTrend(int userId, int portfolioId) throws CryptxException , EmptyDataException {
		String query = String.format(
				"SELECT timestmp, litecoin FROM portfolio_hourly WHERE userid = %d AND portfolioid = %d", userId,
				portfolioId);
		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new EmptyDataException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildPortfolioTrendFromResultSet(rs, "litecoin");
	}

	@Override
	public List<Double[]> getPortfolioEthereumTrend(int userId, int portfolioId) throws CryptxException, EmptyDataException  {
		String query = String.format(
				"SELECT timestmp, ethereum FROM portfolio_hourly WHERE userid = %d AND portfolioid = %d", userId,
				portfolioId);
		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new EmptyDataException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildPortfolioTrendFromResultSet(rs, "ethereum");
	}

	@Override
	public List<Double[]> getPortfolioVirtualWalletTrend(int userId, int portfolioId) throws CryptxException, EmptyDataException  {
		String query = String.format(
				"SELECT timestmp, amount FROM portfolio_hourly WHERE userid = %d AND portfolioid = %d", userId,
				portfolioId);
		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new EmptyDataException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildPortfolioTrendFromResultSet(rs, "amount");
	}

	private List<Double[]> buildPortfolioTrendFromResultSet(ResultSet resultSet, String currency) throws CryptxException{
		List<Double[]> portfolioTrends = new ArrayList<Double[]>();
		long timestamp;
		try {
			while (resultSet.next()) {
				Double[] portfolioTrend = new Double[2];
				timestamp = resultSet.getBigDecimal("timestmp").longValue();
				portfolioTrend[0] = (double) timestamp * 1000;
				portfolioTrend[1] = resultSet.getDouble(currency);
				portfolioTrends.add(portfolioTrend);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return portfolioTrends;
	}
}
