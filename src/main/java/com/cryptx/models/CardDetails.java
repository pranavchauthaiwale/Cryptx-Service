package com.cryptx.models;

public class CardDetails {
	private int cardDetailsId;
	private int userId;
	private String CardName;
	private CardType cardType;
	private long cardNo;
	private String expiry;
	private int CVV;

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

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public static CardDetails getDummyCardDetails() {
		CardDetails dummyCard = new CardDetails();
		dummyCard.setCardDetailsId(1);
		dummyCard.setUserId(1);
		dummyCard.setCardNo(4457889622123656L);
		dummyCard.setExpiry("10/19");
		dummyCard.setCVV(125);
		dummyCard.setCardName("Vineet Ahirkar");
		dummyCard.setCardType(CardType.CREDIT);
		return dummyCard;
	}

}
