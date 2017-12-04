package com.cryptx.services;

import java.util.List;

import com.cryptx.exception.CryptxException;
import com.cryptx.exception.EmptyDataException;
import com.cryptx.models.PaymentMethod;
import com.cryptx.views.PaymentMethodView;

public interface IPaymentMethodService {

	public static final String PAYMENT_METHOD_SERVICE = "PaymentMethodService";

	List<PaymentMethod> getUserPaymentMethodList(int userId) throws CryptxException, EmptyDataException;

	List<PaymentMethod> updatePaymentMethodDetails(PaymentMethodView paymentMethodView, int paymentMethodId, int userId)
			throws CryptxException, EmptyDataException;

	List<PaymentMethod> createUserPaymentMethod(PaymentMethodView paymentMethodView, int userId)
			throws CryptxException, EmptyDataException;

	List<PaymentMethod> deleteUserPaymnetMethod(int cardId, int userId) throws CryptxException, EmptyDataException;
}
