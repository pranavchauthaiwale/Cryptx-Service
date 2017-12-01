package com.cryptx.models;

public class CardDetails {
	private int cardDetailsId;
	private int userId;
	private long cardNo;
	private String expiry;
	private int CVV;
	private String CardName;

	public int getCardDetailsId() {
		return cardDetailsId;
	}

	public void setCardDetailsId(int cardDetailsId) {
		this.cardDetailsId = cardDetailsId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getCardNo() {
		return cardNo;
	}

	public void setCardNo(long cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public int getCVV() {
		return CVV;
	}

	public void setCVV(int cVV) {
		CVV = cVV;
	}

	public String getCardName() {
		return CardName;
	}

	public void setCardName(String cardName) {
		CardName = cardName;
	}

}
