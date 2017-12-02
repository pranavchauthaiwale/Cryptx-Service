package com.cryptx.services;

import java.util.List;

import com.cryptx.exception.CryptxException;
import com.cryptx.models.Transaction;
import com.cryptx.views.TransactionRequestView;
import com.cryptx.views.VirtualWalletView;

public interface ITransactionService {

	public static final String TRANSACTION_SERVICE = "TransactionService";

	void addWalletTransactio(VirtualWalletView userVirtualWallet, int userId) throws CryptxException;

	void doSell(TransactionRequestView transactionRequestView) throws CryptxException;

	void doBuy(TransactionRequestView transactionRequestView) throws CryptxException;

	List<Transaction> getUserTransaction(int userId) throws CryptxException;
}
