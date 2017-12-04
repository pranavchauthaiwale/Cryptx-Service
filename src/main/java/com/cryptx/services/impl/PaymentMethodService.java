package com.cryptx.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptx.dao.IDataAccess;
import com.cryptx.exception.CryptxException;
import com.cryptx.exception.EmptyDataException;
import com.cryptx.models.PaymentMethod;
import com.cryptx.services.IPaymentMethodService;
import com.cryptx.views.PaymentMethodView;

@Service(IPaymentMethodService.PAYMENT_METHOD_SERVICE)
public class PaymentMethodService implements IPaymentMethodService {

	@Autowired
	IDataAccess dataAccess;

	@Override
	public List<PaymentMethod> getUserPaymentMethodList(int userId) throws CryptxException, EmptyDataException {

		String query = String.format(
				"SELECT cardid, userid, nickname, cardnumber, cardname, cardexpirydate, cvv FROM carddetails where userid = %d",
				userId);
		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new EmptyDataException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildPaymentMethodsListFromResultSet(rs);
	}

	@Override
	public List<PaymentMethod> updatePaymentMethodDetails(PaymentMethodView paymentMethodView, int cardId, int userId)
			throws CryptxException, EmptyDataException {

		List<PaymentMethod> userPaymentDetails = getUserPaymentMethodList(userId);
		boolean cardIdPresent = false;
		for (PaymentMethod payment : userPaymentDetails) {
			if (payment.getCardid() == cardId) {
				cardIdPresent = true;
			}
		}
		if (!cardIdPresent) {
			throw new CryptxException("Payment Method Not Found");
		}
		
		String query = String.format("UPDATE carddetails SET nickname = '%s', cardnumber = '%s', cardname = '%s', cardexpirydate = '%s', cvv = '%s' WHERE cardid = %d",
				paymentMethodView.getNickName(), paymentMethodView.getCardNumber(), paymentMethodView.getCardName(), paymentMethodView.getCardExpiryDate(),
				paymentMethodView.getCvv(), cardId);
		
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Creating Payment Methods");
		}
		
		return getUserPaymentMethodList(userId);
	}

	@Override
	public List<PaymentMethod> createUserPaymentMethod(PaymentMethodView paymentMethodView, int userId)
			throws CryptxException, EmptyDataException {
		String query = String.format(
				"INSERT INTO carddetails (userid, nickname, cardnumber, cardname,  cardexpirydate, cvv) VALUES (%d, '%s', '%s', '%s', '%s','%s')",
				userId, paymentMethodView.getNickName(), paymentMethodView.getCardNumber(),
				paymentMethodView.getCardName(), paymentMethodView.getCardExpiryDate(), paymentMethodView.getCvv());
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Creating Payment Methods");
		}
		return getUserPaymentMethodList(userId);
	}

	@Override
	public List<PaymentMethod> deleteUserPaymnetMethod(int cardId, int userId) throws CryptxException, EmptyDataException {

		String query = String.format("DELETE from carddetails WHERE cardid = %d and userid = %d", cardId, userId);

		List<PaymentMethod> userPaymentDetails = getUserPaymentMethodList(userId);
		boolean cardIdPresent = false;
		for (PaymentMethod payment : userPaymentDetails) {
			if (payment.getCardid() == cardId) {
				cardIdPresent = true;
			}
		}
		if (!cardIdPresent) {
			throw new CryptxException("Payment Method Not Found");
		}

		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in Creating Payment Methods");
		}
		userPaymentDetails = getUserPaymentMethodList(userId);
		return userPaymentDetails;
	}

	private List<PaymentMethod> buildPaymentMethodsListFromResultSet(ResultSet resultSet) throws CryptxException {

		List<PaymentMethod> userPaymentMethodList = new ArrayList<PaymentMethod>();
		try {
			while (resultSet.next()) {
				PaymentMethod userPaymentMethod = new PaymentMethod();
				userPaymentMethod.setCardid(resultSet.getInt("cardid"));
				userPaymentMethod.setUserid(resultSet.getInt("userid"));
				userPaymentMethod.setNickName(resultSet.getString("nickname"));
				userPaymentMethod.setCardNumber(resultSet.getString("cardnumber"));
				userPaymentMethod.setCardName(resultSet.getString("cardname"));
				userPaymentMethod.setCardexpirydate(resultSet.getString("cardexpirydate"));
				userPaymentMethod.setCvv(resultSet.getString("cvv"));
				userPaymentMethodList.add(userPaymentMethod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}

		return userPaymentMethodList;
	}

}
