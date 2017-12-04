package com.cryptx.views;

import java.util.List;

import com.cryptx.models.CardDetails;
import com.cryptx.models.CryptxUser;
import com.cryptx.models.Investment;
import com.cryptx.models.PaymentMethod;
import com.cryptx.models.Portfolio;
import com.cryptx.models.Transaction;
import com.cryptx.models.VirtualWallet;

public class UserProfileView {

	private CryptxUser user;
	private VirtualWallet virtualWallet;
	private List<PaymentMethod> paymentMethods;
	private Portfolio portfolio;
	private List<Transaction> transactions;

	public CryptxUser getUser() {
		return user;
	}

	public void setUser(CryptxUser user) {
		this.user = user;
	}

	public VirtualWallet getVirtualWallet() {
		return virtualWallet;
	}

	public void setVirtualWallet(VirtualWallet virtualWallet) {
		this.virtualWallet = virtualWallet;
	}

	public List<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

//	public static UserProfileView getDummyUserProfile() {
//		UserProfileView user = new UserProfileView();
//		user.setUser(CryptxUser.getDummyUserData());
//		user.setVirtualWallet(VirtualWallet.getDummyVirtualWalletDetails());
//		user.setPaymentMethods(PaymentMethod.getDummyPaymentMethodsList());
//		user.setTransactions(Transaction.getDummyTransactions());
//		user.setPortfolio(Portfolio.getDummyPortfolio());
//		return user;
//	}
}
