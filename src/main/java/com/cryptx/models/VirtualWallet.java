package com.cryptx.models;

public class VirtualWallet {

	private int virtualWalletId;
	private int userId;
	private double amount;

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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public static VirtualWallet getDummyVirtualWalletDetails() {
		VirtualWallet dummyVirtualWallet = new VirtualWallet();
		dummyVirtualWallet.setUserId(1);
		dummyVirtualWallet.setVirtualWalletId(1);
		dummyVirtualWallet.setAmount(100000);
		return dummyVirtualWallet;
	}

}
