package com.cryptx.views;

import java.util.List;

import com.cryptx.models.CardDetails;
import com.cryptx.models.CryptxUser;
import com.cryptx.models.Investment;
import com.cryptx.models.PaymentMethod;
import com.cryptx.models.Transaction;
import com.cryptx.models.VirtualWallet;

public class UserProfileView {

	private CryptxUser user;
	private VirtualWallet virtualWallet;
	private CardDetails cardDetails;
	private List<PaymentMethod> paymentMethods;
	private List<Investment> investments;
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

	public CardDetails getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}

	public List<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public List<Investment> getInvestments() {
		return investments;
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public static UserProfileView getDummyUserProfile() {
		UserProfileView user = new UserProfileView();
		user.setUser(CryptxUser.getDummyUserData());
		user.setVirtualWallet(VirtualWallet.getDummyVirtualWalletDetails());
		user.setCardDetails(CardDetails.getDummyCardDetails());
		user.setPaymentMethods(PaymentMethod.getDummyPaymentMethodsList());
		user.setInvestments(Investment.getDummyInvestments());
		user.setTransactions(Transaction.getDummyTransactions());
		return user;
	}
}
