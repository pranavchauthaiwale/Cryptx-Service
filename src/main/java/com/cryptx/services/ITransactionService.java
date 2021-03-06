package com.cryptx.services;

import java.util.List;

import com.cryptx.exception.CryptxException;
import com.cryptx.models.Transaction;
import com.cryptx.views.TransactionRequestView;
import com.cryptx.views.VirtualWalletView;

public interface ITransactionService {

	public static final String TRANSACTION_SERVICE = "TransactionService";

	void recordWalletDeposit(VirtualWalletView userVirtualWallet, int userId) throws CryptxException;
	
	void recordWalletithdraw(VirtualWalletView userVirtualWallet, int userId) throws CryptxException;

	void doSell(TransactionRequestView transactionRequestView, int userId) throws CryptxException;

	void doBuy(TransactionRequestView transactionRequestView, int userId) throws CryptxException;

	List<Transaction> getUserTransaction(int userId) throws CryptxException;
}
