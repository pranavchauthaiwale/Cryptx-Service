package com.cryptx.models;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethod {
	private int cardId;
	private int userId;
	private String nickName;
	private String cardName;
	private String cardNumber;
	private String cardexpirydate;
	private String cvv;

	public int getCardid() {
		return cardId;
	}

	public void setCardid(int cardId) {
		this.cardId = cardId;
	}

	public int getUserid() {
		return userId;
	}

	public void setUserid(int userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCardexpirydate() {
		return cardexpirydate;
	}

	public void setCardexpirydate(String cardexpirydate) {
		this.cardexpirydate = cardexpirydate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public static List<PaymentMethod> getDummyPaymentMethodsList() {
		List<PaymentMethod> dummyPaymentMethodList = new ArrayList<PaymentMethod>();
		PaymentMethod paymentOne = new PaymentMethod();
		paymentOne.setCardid(1);
		paymentOne.setUserid(2);
		paymentOne.setNickName("PNC Bank");
		paymentOne.setCardName("Vineet Ahirkar");
		paymentOne.setCardNumber("123456789");
		paymentOne.setCardexpirydate("10/19");
		paymentOne.setCvv("789");

		PaymentMethod paymentTwo = new PaymentMethod();
		paymentTwo.setCardid(2);
		paymentTwo.setUserid(2);
		paymentTwo.setNickName("Bank of America");
		paymentTwo.setCardName("Vineet Ahirkar");
		paymentTwo.setCardNumber("987654321");
		paymentTwo.setCardexpirydate("3/21");
		paymentTwo.setCvv("653");

		dummyPaymentMethodList.add(paymentOne);
		dummyPaymentMethodList.add(paymentTwo);
		return dummyPaymentMethodList;
	}

}
