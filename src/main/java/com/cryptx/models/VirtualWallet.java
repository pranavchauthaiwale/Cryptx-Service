package com.cryptx.models;

public class VirtualWallet {

	private int virtualWalletId;
	private int userId;
	private double balance;

	public int getVirtualWalletId() {
		return virtualWalletId;
	}

	public void setVirtualWalletId(int virtualWalletId) {
		this.virtualWalletId = virtualWalletId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
