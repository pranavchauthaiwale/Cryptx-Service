package com.cryptx.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptx.dao.IDataAccess;
import com.cryptx.exception.CryptxException;
import com.cryptx.models.Transaction;
import com.cryptx.services.ITransactionService;
import com.cryptx.views.TransactionRequestView;
import com.cryptx.views.VirtualWalletView;

@Service(ITransactionService.TRANSACTION_SERVICE)
public class TransactionService implements ITransactionService {

	@Autowired
	IDataAccess dataAccess;

	@Override
	public void recordWalletDeposit(VirtualWalletView virtualWalletView, int userId) throws CryptxException {
		Date date = new Date();
		String query = String.format(
				"INSERT INTO usertransaction ( portfolioid, userid, type, currency, numberofcoins, transactionamount, transactiontime ) VALUES ( %d, %d, '%s', '%s', %f, %f, %ts )",
				virtualWalletView.getPortfolioId(), userId, "Deposit", "USD", virtualWalletView.getAmount(),
				virtualWalletView.getAmount(), date);
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Wallet Deposit Transaction");
		}
	}

	@Override
	public void recordWalletithdraw(VirtualWalletView virtualWalletView, int userId) throws CryptxException {
		Date date = new Date();
		String query = String.format(
				"INSERT INTO usertransaction ( portfolioid, userid, type, currency, numberofcoins, transactionamount, transactiontime ) VALUES ( %d, %d, '%s', '%s', %f, %f, %ts )",
				virtualWalletView.getPortfolioId(), userId, "Withdraw", "USD", virtualWalletView.getAmount(),
				virtualWalletView.getAmount(), date);
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Wallet Withdraw Transaction");
		}
	}

	@Override
	public List<Transaction> getUserTransaction(int userId) throws CryptxException {
		String query = String.format(
				"SELECT userid, transactionid, portfolioid, type, currency, numberofcoins, transactionamount, transactiontime FROM usertransaction where userId = %d", userId);
		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new CryptxException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}

		return buildTransactionListFromResultSet(rs);
	}

	@Override
	public void doSell(TransactionRequestView transactionRequest, int userId) throws CryptxException {
		String query = String.format(
				"INSERT INTO usertransaction ( portfolioid, userid, type, currency, numberofcoins, transactionamount, transactiontime ) VALUES ( %d, %d, '%s', '%s', %f, %f, %f)",
				transactionRequest.getPortfolioId(), userId, "SELL",
				transactionRequest.getCurrency(), transactionRequest.getNumberOfCoins(),
				transactionRequest.getTransactionAmount(), transactionRequest.getTransactionTime());
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Sell Transaction");
		}
	}

	@Override
	public void doBuy(TransactionRequestView transactionRequest, int userId) throws CryptxException {
		String query = String.format(
				"INSERT INTO usertransaction ( portfolioid, userid, type, currency, numberofcoins, transactionamount, transactiontime ) VALUES ( %d, %d, '%s', '%s', %f, %f, %f)",
				transactionRequest.getPortfolioId(), userId, "BUY",
				transactionRequest.getCurrency(), transactionRequest.getNumberOfCoins(),
				transactionRequest.getTransactionAmount(), transactionRequest.getTransactionTime());
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Buy Transaction");
		}

	}

	private List<Transaction> buildTransactionListFromResultSet(ResultSet resultSet) throws CryptxException {
		List<Transaction> userTransactionList = new ArrayList<Transaction>();
		try {
			while (resultSet.next()) {
				Transaction userTransaction = new Transaction();
				userTransaction.setTransactionId(resultSet.getInt("transactionid"));
				userTransaction.setPortfolioId(resultSet.getInt("portfolioid"));
				userTransaction.setUserId(resultSet.getInt("userid"));
				userTransaction.setType(resultSet.getString("type"));
				userTransaction.setCurrency(resultSet.getString("currency"));
				userTransaction.setNumberOfCoins(resultSet.getDouble("numberofcoins"));
				userTransaction.setTransactionAmount(resultSet.getDouble("transactionamount"));
				userTransaction.setTransactionTime(resultSet.getBigDecimal("transactiontime").longValue());
				userTransactionList.add(userTransaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return userTransactionList;
	}
}
