package com.cryptx.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptx.dao.IDataAccess;
import com.cryptx.exception.CryptxException;
import com.cryptx.models.Portfolio;
import com.cryptx.models.VirtualWallet;
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
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildPortfolioFromResultSet(rs);
	}

	@Override
	public Portfolio updatePortfolioAmount(double amount, int userId) throws CryptxException {

		Portfolio userPortfolio = getUserPortfolio(userId);

		if (userPortfolio == null) {
			createUserPortfolio(userId);
		}

		String query = String.format("UPDATE wallet SET amount = %f WHERE userid = %d", amount,
				userId);

		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Updating Virtual Amount in Portfolio");
		}
		return getUserPortfolio(userId);
	}

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

}
