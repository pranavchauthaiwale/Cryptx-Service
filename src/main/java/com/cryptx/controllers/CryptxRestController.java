package com.cryptx.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cryptx.exception.CryptxException;
import com.cryptx.models.CryptxUser;
import com.cryptx.models.PaymentMethod;
import com.cryptx.models.Transaction;
import com.cryptx.models.VirtualWallet;
import com.cryptx.services.IUserService;
import com.cryptx.services.IVirtualWalletService;
import com.cryptx.views.UserProfileView;
import com.cryptx.views.VirtualWalletView;

@CrossOrigin
@RestController
@RequestMapping("/")
public class CryptxRestController {

	private static final String message = "message";
	private static final String data = "data";
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IVirtualWalletService virtualWalletService;

	private static final Logger logger = LoggerFactory.getLogger(CryptxRestController.class);

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<?> loginUser(Principal principal) {
		logger.info(String.format("User Login: [%s]", principal.getName()));
		Map<String, Object> resource = new HashMap<String, Object>();
		//
		//resource = getDummyUserData();
		resource.put(message, "User Login Successful");
		resource.put(data, getDummyProfile());
		//
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpSession session) {
		logger.info("Loggin-out User. Invalidating Session");
		session.invalidate();
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<?> signup(@RequestBody Map<String, CryptxUser> userView) {
		logger.info("Registering new user in cryptx");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "User Created Successfully");
			userService.createNewUser(userView.get("user"));
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Registration of New User Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "user/payment_details", method = RequestMethod.GET)
	public ResponseEntity<?> getPaymentDetails() {
		logger.info("Request for bank details of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "User Payment Details");
			resource.put(data, PaymentMethod.getDummyPaymentMethodsList());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Payment Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "payment_method/create", method = RequestMethod.POST)
	public ResponseEntity<?> addPaymentDetails() {
		logger.info("Request for adding bank details for user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "Bank Details Added Successfully");
			resource.put(data, PaymentMethod.getDummyPaymentMethodsList());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Add Bank Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "payment_method/{paymentMethodId}/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePaymentDetails(@PathVariable int paymentMethodId) {
		logger.info("Requesting for updating bank details of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "Payment Details Updated Successfully");
			resource.put(data, PaymentMethod.getDummyPaymentMethodsList());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Update Payment Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "payment_method/{paymentMethodId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePaymentDetails(@PathVariable int paymentMethodId) {
		logger.info("Deleting bank details of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "Payment Details Deleted Successfully");
			resource.put(data, PaymentMethod.getDummyPaymentMethodsList());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Delete Payment Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "currencyhistory/{currency}", method = RequestMethod.GET)
	public ResponseEntity<?> getCurrencyHistory(@PathVariable String currency) {
		logger.info(String.format("Requesting historic data of currency [%s]", currency));
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "Historic Currency Details");
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Request Historical Currency Trends Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "wallet/details", method = RequestMethod.GET)
	public ResponseEntity<?> getUserVirtualWalletDetails() {
		logger.info("Requesting virtual wallet information of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {	
			resource.put(message, "User Virtual Wallet Details");
			resource.put(data, VirtualWallet.getDummyVirtualWalletDetails());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Request for User Wallet Details Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	// @RequestMapping(value="addvirtualwallet", method=RequestMethod.POST)
	// public Map<String, String> addVirtualWallet() {
	// logger.info("Requesting for adding new virtual wallet for user");
	// Map<String, String> resource = new HashMap<String, String>();
	// resource.put("resource", "Add Virtual Wallet Resource");
	// return resource;
	// }

	@RequestMapping(value = "wallet/deposit", method = RequestMethod.POST)
	public ResponseEntity<?> depositInVirtualWallet(@RequestBody Map<String, VirtualWalletView> walletView, Principal principal) {
		logger.info("Requesting for depositing in virtual wallet of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			CryptxUser loggedInUser = userService.findUserByEmail(principal.getName());
			virtualWalletService.deposit(walletView.get("walletRequest"), loggedInUser.getUserId());
			resource.put(message, "Deposited in Virtual Wallet Successfully");
			resource.put(data, VirtualWallet.getDummyVirtualWalletDetails());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Deposit Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "wallet/withdraw", method = RequestMethod.POST)
	public ResponseEntity<?> withdrawFromVirtualWallet(@RequestBody Map<String, VirtualWalletView> walletView, Principal principal) {
		logger.info("Requesting for withdrawing from virtual wallet of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			CryptxUser loggedInUser = userService.findUserByEmail(principal.getName());
			virtualWalletService.withdraw(walletView.get("walletRequest"), loggedInUser.getUserId());
			resource.put(message, "Withdrawn from Virtual Wallet Successfully");
			resource.put(data, VirtualWallet.getDummyVirtualWalletDetails());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Withdraw Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "currency/buy", method = RequestMethod.POST)
	public ResponseEntity<?> buy() {
		logger.info("Requesting for buying specified currency");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "Currency Buy Transaction Successful");
			resource.put(data, Transaction.getDummyTransactions());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Currecny Buy Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "currency/sell", method = RequestMethod.POST)
	public ResponseEntity<?> sell() {
		logger.info("Requesting for selling specified currencyr");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "Sell Currency Transaction Succesful");
			resource.put(data, Transaction.getDummyTransactions());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Currecny Sell Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "user/profile", method = RequestMethod.GET)
	public ResponseEntity<?> getUserProfile(Principal principal) {
		logger.info("Retrieving user profile informations");
		logger.info("Logged in user is: " + principal.getName() );
		Map<String, Object> resource = new HashMap<String, Object>();
		//Map<String, Object> resource = getDummyUserData();
		try {
			resource.put(message, "User profile details");
			resource.put(data, getDummyProfile());
			
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Request for User Profile Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "user/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUserProfile() {
		logger.info("Updating user profile informations");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "User Profile Updated Successfully");
			resource.put(data, getDummyProfile());
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Request for Updating User Profile Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}
	
	private Map<String, Object> getDummyUserData(){
		
		Map<String, Object> resource = new HashMap<String, Object>();
		//User Data
		Map<String, Object> userData = new HashMap<String, Object>(); 
		userData.put("name", "Vineet");
		userData.put("email", "vinzee93@gmail.com");
		userData.put("address", "Maryland");
		userData.put("city", "Baltimore");
		userData.put("country", "United States");
		userData.put("postalCode", "21227");
		resource.put("user", userData);
		
		//Wallet Data
		Map<String, Object> walletData = new HashMap<String, Object>();
		walletData.put("balance", 123);
		resource.put("virtualWallet", walletData);
		
		//Bank Data
		Map<String, Object> paymentMathodOne = new HashMap<String, Object>();
		paymentMathodOne.put("id", "1");
		paymentMathodOne.put("name", "Bank of America");
		paymentMathodOne.put("account_number", "1234");
		paymentMathodOne.put("type", "credit");
		
		Map<String, Object> paymentMethodTwo = new HashMap<String, Object>();
		paymentMethodTwo.put("id", "2");
		paymentMethodTwo.put("name", "PNC");
		paymentMethodTwo.put("account_number", "6789");
		paymentMethodTwo.put("type", "debit");
		
		List<Map<String, Object>> paymentMethodss = new ArrayList<Map<String,Object>>();
		paymentMethodss.add(paymentMathodOne);
		paymentMethodss.add(paymentMethodTwo);
		resource.put("paymentMethods", paymentMethodss);
		
		//Investment Data
		Map<String, Object> investOne = new HashMap<String, Object>();
		investOne.put("currency", "Bitcoin");
		investOne.put("amount", 0.4);
		
		Map<String, Object> investTwo = new HashMap<String, Object>();
		investTwo.put("currency", "Litecoin");
		investTwo.put("amount", 2.3);
		
		Map<String, Object> investThree = new HashMap<String, Object>();
		investThree.put("currency", "Ethereum");
		investThree.put("amount", 3.5);
		
		Map<String, Object> investFour = new HashMap<String, Object>();
		investFour.put("currency", "Ripple");
		investFour.put("amount", 0);
		
		List<Map<String, Object>> investments = new ArrayList<Map<String,Object>>();
		investments.add(investOne);
		investments.add(investTwo);
		investments.add(investThree);
		investments.add(investFour);
		resource.put("investments", investments);
		
		//Transaction Data
		Map<String, Object> transOne = new HashMap<String, Object>();
		transOne.put("type", "Buy");
		transOne.put("currency", "Bitcoin");
		transOne.put("amount", "$36.738");
		transOne.put("date", 1510930059);
		
		Map<String, Object> transTwo = new HashMap<String, Object>();
		transTwo.put("type", "Buy");
		transTwo.put("currency", "Bitcoin");
		transTwo.put("amount", "$36.738");
		transTwo.put("date", 1510930059);
		
		Map<String, Object> transThree = new HashMap<String, Object>();
		transThree.put("type", "Buy");
		transThree.put("currency", "Bitcoin");
		transThree.put("amount", "$36.738");
		transThree.put("date", 1510930059);
		
		Map<String, Object> transFour = new HashMap<String, Object>();
		transFour.put("type", "Buy");
		transFour.put("currency", "Bitcoin");
		transFour.put("amount", "$36.738");
		transFour.put("date", 1510930059);
		
		List<Map<String, Object>> transactions = new ArrayList<Map<String,Object>>();
		transactions.add(transOne);
		transactions.add(transTwo);
		transactions.add(transThree);
		transactions.add(transFour);
		resource.put("transactions", transactions);
		return resource;
	}
	
	private UserProfileView getDummyProfile() {
		return UserProfileView.getDummyUserProfile();
	}
	
}
