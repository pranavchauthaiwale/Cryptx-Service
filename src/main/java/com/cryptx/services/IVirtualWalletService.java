package com.cryptx.services;

import com.cryptx.exception.CryptxException;
import com.cryptx.models.VirtualWallet;
import com.cryptx.views.VirtualWalletView;

public interface IVirtualWalletService {
	
	public static final String VIRTUAL_WALLET_SERVICE = "VirtualWalletService";
	VirtualWallet deposit(VirtualWalletView virtualWalletView, int userId) throws CryptxException;
	VirtualWallet withdraw(VirtualWalletView virtualWalletView, int userId) throws CryptxException;
	VirtualWallet getUserVirtualWallet(int userId) throws CryptxException;
}
