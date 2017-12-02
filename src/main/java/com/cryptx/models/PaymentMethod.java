package com.cryptx.models;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethod {
	private int paymentMethodId;
	private int userid;
	private String nickName;
	private long accountNo;
	private long routingNo;
	private String accountUserName;

	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public long getRoutingNo() {
		return routingNo;
	}

	public void setRoutingNo(long routingNo) {
		this.routingNo = routingNo;
	}

	public String getAccountUserName() {
		return accountUserName;
	}

	public void setAccountUserName(String accountUserName) {
		this.accountUserName = accountUserName;
	}

	public static List<PaymentMethod> getDummyPaymentMethodsList() {
		List<PaymentMethod> dummyPaymentMethodList = new ArrayList<PaymentMethod>();
		PaymentMethod paymentOne = new PaymentMethod();
		paymentOne.setPaymentMethodId(1);
		paymentOne.setUserid(1);
		paymentOne.setNickName("PNC Bank");
		paymentOne.setAccountNo(1234);
		paymentOne.setRoutingNo(5555);
		paymentOne.setAccountUserName("Vineet Ahirkar");

		PaymentMethod paymentTwo = new PaymentMethod();
		paymentTwo.setPaymentMethodId(2);
		paymentTwo.setUserid(1);
		paymentTwo.setNickName("Bank of America");
		paymentTwo.setAccountNo(6789);
		paymentTwo.setRoutingNo(8888);
		paymentTwo.setAccountUserName("Vineet Ahirkar");

		dummyPaymentMethodList.add(paymentOne);
		dummyPaymentMethodList.add(paymentTwo);
		return dummyPaymentMethodList;
	}

}
