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
	public void addWalletTransactio(VirtualWalletView virtualWalletView, int userId) throws CryptxException {
		Date date = new Date();
		String query = String.format("INSERT INTO usertransaction ( portfolioid, userid, type, currency, numberofcoins, transactionamount, transactiontime ) VALUES ( %d, %d, %s, %s, %d, %d, %ts )",
				virtualWalletView.getPortfolioId(), userId, "WalletTransaction", "USD", 
				virtualWalletView.getAmount(), virtualWalletView.getAmount(), date);
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Updating Transaction");
		}
	}

	@Override
	public List<Transaction> getUserTransaction(int userId) throws CryptxException {
		String query = String.format("SELECT transactionid, portfolioid, trans_type, transtime FROM usertransaction where userid = %d", userId);
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
	public void doSell(TransactionRequestView transactionRequestView) throws CryptxException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doBuy(TransactionRequestView transactionRequestView) throws CryptxException {
		// TODO Auto-generated method stub
		
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
				userTransaction.setTransactionAmount(resultSet.getDouble("transacationamount"));
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
