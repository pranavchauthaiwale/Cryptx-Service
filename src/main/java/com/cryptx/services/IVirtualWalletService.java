package com.cryptx.services;

import com.cryptx.exception.CryptxException;
import com.cryptx.models.VirtualWallet;

public interface IVirtualWalletService {

	public static final String VIRTUAL_WALLET_SERVICE = "VirtualWalletService";

	boolean deposit(double amount, int userId) throws CryptxException;

	boolean withdraw(double amount, int userId) throws CryptxException;

	VirtualWallet getUserVirtualWallet(int userId) throws CryptxException;
}
