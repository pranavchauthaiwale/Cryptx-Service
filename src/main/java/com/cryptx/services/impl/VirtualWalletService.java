package com.cryptx.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptx.dao.IDataAccess;
import com.cryptx.exception.CryptxException;
import com.cryptx.models.VirtualWallet;
import com.cryptx.services.IVirtualWalletService;

@Service(IVirtualWalletService.VIRTUAL_WALLET_SERVICE)
public class VirtualWalletService implements IVirtualWalletService {

	@Autowired
	IDataAccess dataAccess;

	@Override
	public boolean deposit(double amount, int userId) throws CryptxException {

		VirtualWallet userVirtualWallet = getUserVirtualWallet(userId);
		if (userVirtualWallet == null) {
			userVirtualWallet = createUserVirtualWallet(userId);
		}
		depositIntoVirtualWallet(amount, userVirtualWallet);
		return true;
	}

	@Override
	public boolean withdraw(double amount, int userId) throws CryptxException {

		VirtualWallet userVirtualWallet = getUserVirtualWallet(userId);
		if (userVirtualWallet == null) {
			createUserVirtualWallet(userId);
			throw new CryptxException("Insufficient Funds");
		}
		withdrawFromVirtualWallet(amount, userVirtualWallet);
		return true;
	}

	@Override
	public VirtualWallet getUserVirtualWallet(int userId) throws CryptxException {
		String query = "SELECT userid, walletid, amount FROM wallet WHERE userid = " + String.valueOf(userId);
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

		return buildVirtualWalletFromResultSet(rs);
	}

	// private VirtualWallet tryGetVirtualWalletInfo(int userId) throws
	// CryptxException {
	// String query = "SELECT userid, walletid, amount FROM wallet WHERE userid = "
	// + String.valueOf(userId);
	// ResultSet rs;
	// try {
	// rs = dataAccess.executeQuery(query);
	// if (!rs.isBeforeFirst()) {
	// return null;
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// throw new CryptxException("SQL Exception. Error: " + e.getMessage());
	// }
	//
	// return buildVirtualWalletFromResultSet(rs);
	// }

	private VirtualWallet buildVirtualWalletFromResultSet(ResultSet rs) throws CryptxException {

		VirtualWallet virtualWallet = new VirtualWallet();
		try {
			while (rs.next()) {
				virtualWallet.setUserId(rs.getInt("userid"));
				virtualWallet.setVirtualWalletId(rs.getInt("walletid"));
				virtualWallet.setAmount(rs.getInt("amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return virtualWallet;
	}

	// private void insertIntoVirtualWallet(VirtualWalletView virtualWalletView, int
	// userId) throws CryptxException {
	// String query = String.format("INSERT INTO wallet (userid, amount) values (%d,
	// %f);", userId,
	// virtualWalletView.getAmount());
	// try {
	// dataAccess.executeQuery(query);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// throw new CryptxException("Error in Inserting new wallet information");
	// }
	// }

	private void depositIntoVirtualWallet(double amount, VirtualWallet virtualWallet) throws CryptxException {
		double newAmount = virtualWallet.getAmount() + amount;
		String query = String.format("UPDATE wallet SET amount = %f WHERE userid = %d", newAmount,
				virtualWallet.getUserId());
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in updating wallet information");
		}
	}

	private void withdrawFromVirtualWallet(double amount, VirtualWallet virtualWallet) throws CryptxException {
		double newAmount = virtualWallet.getAmount() - amount;
		if (newAmount < 0) {
			throw new CryptxException("Insufficient Funds");
		}
		String query = String.format("UPDATE wallet SET amount = %f WHERE userid = %d;", newAmount,
				virtualWallet.getUserId());
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in updating wallet information");
		}
	}

	public VirtualWallet createUserVirtualWallet(int userId) throws CryptxException {
		String query = String.format("INSERT INTO WALLET (userid, amount) VALUES (%d, 0)", userId);

		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Creating Portfolio for user");
		}
		return getUserVirtualWallet(userId);
	}
}
