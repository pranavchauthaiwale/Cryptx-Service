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
import com.cryptx.exception.EmptyDataException;
import com.cryptx.models.CryptxUser;
import com.cryptx.models.EmptyResponse;
import com.cryptx.models.PaymentMethod;
import com.cryptx.models.Portfolio;
import com.cryptx.models.Transaction;
import com.cryptx.models.VirtualWallet;
import com.cryptx.services.IHistoricTrendsService;
import com.cryptx.services.IPaymentMethodService;
import com.cryptx.services.IPortfolioService;
import com.cryptx.services.ITransactionService;
import com.cryptx.services.IUserService;
import com.cryptx.services.IVirtualWalletService;
import com.cryptx.views.PaymentMethodView;
import com.cryptx.views.TransactionRequestView;
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

	@Autowired
	ITransactionService transactionService;

	@Autowired
	IPortfolioService portfolioService;

	@Autowired
	IPaymentMethodService paymentMethodService;

	@Autowired
	IHistoricTrendsService historicTrendsService;

	private static final Logger logger = LoggerFactory.getLogger(CryptxRestController.class);

	private final static EmptyResponse EMPTY_RESPONSE = new EmptyResponse();

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<?> loginUser(Principal principal) {
		String userName = principal.getName();
		logger.info(String.format("User Login: [%s]", userName));
		Map<String, Object> resource = new HashMap<String, Object>();
		resource.put(message, "User Login Successful");
		try {
			resource.put(data, getUserProfileView(userName));
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "User Login Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
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
			CryptxUser newUser = userView.get("user");
			resource.put(message, "User Created Successfully");
			userService.createNewUser(newUser);

			newUser = userService.findUserByEmail(newUser.getEmail());

			virtualWalletService.createUserVirtualWallet(newUser.getUserId());

			portfolioService.createUserPortfolio(newUser.getUserId());
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

	@RequestMapping(value = "user/profile", method = RequestMethod.GET)
	public ResponseEntity<?> getUserProfile(Principal principal) {
		String userName = principal.getName();
		logger.info(String.format("User Profile Request: [%s]", userName));
		logger.info("Retrieving user profile informations");
		logger.info("Logged in user is: " + principal.getName());
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "User profile details");
			resource.put(data, getUserProfileView(userName));
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "User Profile Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "user/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUserProfile(@RequestBody Map<String, CryptxUser> userView, Principal principal) {
		String userName = principal.getName();
		logger.info("Updating user profile informations of user [%s]", userName);

		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(data, userService.updateUserDetails(userView.get("user"), userName));
			resource.put(message, "User Profile Updated Successfully");
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Request for Updating User Profile Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "user/payment_details", method = RequestMethod.GET)
	public ResponseEntity<?> getPaymentDetails(Principal principal) {
		logger.info("Request for bank details of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			int userId = userService.findUserByEmail(principal.getName()).getUserId();
			resource.put(message, "User Payment Details");
			resource.put(data, paymentMethodService.getUserPaymentMethodList(userId));
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, "No User Payment Methods Found");
			resource.put(data, EMPTY_RESPONSE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (EmptyDataException e) {
			resource.put(message, "No Payment Methods Found");
			resource.put(data, EMPTY_RESPONSE);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Payment Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "payment_method/create", method = RequestMethod.POST)
	public ResponseEntity<?> addPaymentDetails(@RequestBody Map<String, PaymentMethodView> paymentMethodView,
			Principal principal) {
		logger.info("Request for adding bank details for user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			int userId = userService.findUserByEmail(principal.getName()).getUserId();
			PaymentMethodView paymentMethod = paymentMethodView.get("paymentMethod");
			List<PaymentMethod> userPaymentMethods = paymentMethodService.createUserPaymentMethod(paymentMethod,
					userId);
			resource.put(message, "Bank Details Added Successfully");
			resource.put(data, userPaymentMethods);
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, "Error in Adding Payment Method");
			resource.put(data, EMPTY_RESPONSE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (EmptyDataException e) {
			resource.put(message, "No Payment Methods Found");
			resource.put(data, EMPTY_RESPONSE);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Add Bank Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "payment_method/{paymentMethodId}/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePaymentDetails(@PathVariable int paymentMethodId,
			@RequestBody Map<String, PaymentMethodView> paymentMethodView, Principal principal) {
		logger.info("Requesting for updating bank details of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			int userId = userService.findUserByEmail(principal.getName()).getUserId();
			PaymentMethodView paymentMethod = paymentMethodView.get("paymentMethod");
			resource.put(message, "Payment Details Updated Successfully");
			resource.put(data, paymentMethodService.updatePaymentMethodDetails(paymentMethod, paymentMethodId, userId));
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, "Error in Updating Payment Method");
			resource.put(data, EMPTY_RESPONSE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Update Payment Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "payment_method/{paymentMethodId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePaymentDetails(@PathVariable int paymentMethodId, Principal principal) {
		logger.info("Deleting bank details of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			int userId = userService.findUserByEmail(principal.getName()).getUserId();
			resource.put(message, "Payment Details Deleted Successfully");
			resource.put(data, paymentMethodService.deleteUserPaymnetMethod(paymentMethodId, userId));
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, "Error Deleting Method");
			resource.put(data, EMPTY_RESPONSE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (EmptyDataException e) {
			resource.put(message, "No Payment Methods Found");
			resource.put(data, EMPTY_RESPONSE);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Delete Payment Details Request Failed Unexpectedly");
			resource.put(data, EMPTY_RESPONSE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "currencyhistory", method = RequestMethod.GET)
	public ResponseEntity<?> getCurrencyHistory() {
		logger.info(String.format("Requesting historic data of Crypto-Curriencies"));
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put(message, "Historic Currency Details");
			resource.put("bitcoin", historicTrendsService.getBitcoinHistoricTrends());
			resource.put("litecoin", historicTrendsService.getLitecoinHistoricTrends());
			resource.put("ethereum", historicTrendsService.getEthereumHistoricTrends());
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, "Error Deleting Method");
			resource.put(data, EMPTY_RESPONSE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Request Historical Currency Trends Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "portfoliohistory", method = RequestMethod.GET)
	public ResponseEntity<?> getPortfolioHistory() {
		logger.info(String.format("Requesting historic data of User Portfolio"));
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			List<Double[]> responseArray = new ArrayList<Double[]>();
			Double[] arr1 = new Double[2];
			arr1[0] = 44587789224D;
			arr1[1] = 6.6;

			Double[] arr2 = new Double[2];
			arr2[0] = 1154877598D;
			arr2[1] = 18.6;
			responseArray.add(arr1);
			responseArray.add(arr2);
			resource.put(message, "Historic trends of User Portfolio");
			resource.put(data, responseArray);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Request Historical Portfolio Trends Failed Unexpectedly");
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
	public ResponseEntity<?> depositInVirtualWallet(@RequestBody Map<String, VirtualWalletView> walletView,
			Principal principal) {
		logger.info("Requesting for depositing in virtual wallet of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			VirtualWalletView virtualWalletView = walletView.get("walletRequest");
			int userId = userService.findUserByEmail(principal.getName()).getUserId();

			virtualWalletService.deposit(virtualWalletView.getAmount(), userId);

			VirtualWallet userWallet = virtualWalletService.getUserVirtualWallet(userId);

			transactionService.recordWalletDeposit(virtualWalletView, userId);

			portfolioService.updatePortfolio("USD", userWallet.getAmount(), userId);

			resource.put(message, "Deposited in Virtual Wallet Successfully");
			// List<Object> list = new ArrayList<Object>()
			// list.add(userWallet);
			// list.add(transactionService.getUserTransaction(userId));

			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("virtualWallet", userWallet);
			responseMap.put("transactions", transactionService.getUserTransaction(userId));
			resource.put(data, responseMap);
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Deposit Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "wallet/withdraw", method = RequestMethod.POST)
	public ResponseEntity<?> withdrawFromVirtualWallet(@RequestBody Map<String, VirtualWalletView> walletView,
			Principal principal) {
		logger.info("Requesting for withdrawing from virtual wallet of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			VirtualWalletView virtualWalletView = walletView.get("walletRequest");
			int userId = userService.findUserByEmail(principal.getName()).getUserId();

			virtualWalletService.withdraw(virtualWalletView.getAmount(), userId);

			VirtualWallet userWallet = virtualWalletService.getUserVirtualWallet(userId);

			transactionService.recordWalletithdraw(virtualWalletView, userId);

			portfolioService.updatePortfolio("USD", userWallet.getAmount(), userId);

			resource.put(message, "Withdrawn from Virtual Wallet Successfully");
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("virtualWallet", userWallet);
			responseMap.put("transactions", transactionService.getUserTransaction(userId));
			resource.put(data, responseMap);
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Withdraw Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "currency/buy", method = RequestMethod.POST)
	public ResponseEntity<?> buy(@RequestBody Map<String, TransactionRequestView> transactionRequestView,
			Principal principal) {
		logger.info("Requesting for buying specified currency");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			int userId = userService.findUserByEmail(principal.getName()).getUserId();
			TransactionRequestView transactionRequest = transactionRequestView.get("transactionRequest");

			virtualWalletService.withdraw(transactionRequest.getTransactionAmount(), userId);

			transactionService.doBuy(transactionRequest, userId);

			double currentCoins = portfolioService.getAmount(transactionRequest.getCurrency(), userId);
			double newAmount = currentCoins + transactionRequest.getNumberOfCoins();

			portfolioService.updatePortfolio(transactionRequest.getCurrency(), newAmount, userId);

			VirtualWallet userWallet = virtualWalletService.getUserVirtualWallet(userId);

			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("virtualWallet", userWallet);
			responseMap.put("transactions", transactionService.getUserTransaction(userId));
			responseMap.put("portfolio", portfolioService.getUserPortfolio(userId));
			resource.put(message, "Currency Buy Transaction Successful");
			resource.put(data, responseMap);
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Currecny Buy Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "currency/sell", method = RequestMethod.POST)
	public ResponseEntity<?> sell(@RequestBody Map<String, TransactionRequestView> transactionRequestView,
			Principal principal) {
		logger.info("Requesting for selling specified currencyr");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			int userId = userService.findUserByEmail(principal.getName()).getUserId();
			TransactionRequestView transactionRequest = transactionRequestView.get("transactionRequest");

			double currentCoins = portfolioService.getAmount(transactionRequest.getCurrency(), userId);
			double newAmount = currentCoins - transactionRequest.getNumberOfCoins();
			if (newAmount < 0) {
				throw new CryptxException("Insufficient Coins");
			}

			portfolioService.updatePortfolio(transactionRequest.getCurrency(), newAmount, userId);

			transactionService.doSell(transactionRequest, userId);

			virtualWalletService.deposit(transactionRequest.getTransactionAmount(), userId);

			VirtualWallet userWallet = virtualWalletService.getUserVirtualWallet(userId);

			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("virtualWallet", userWallet);
			responseMap.put("transactions", transactionService.getUserTransaction(userId));
			responseMap.put("portfolio", portfolioService.getUserPortfolio(userId));
			resource.put(message, "Currency Sell Transaction Successful");
			resource.put(data, responseMap);
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put(message, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put(message, "Currecny Sell Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	private Map<String, Object> getDummyUserData() {

		Map<String, Object> resource = new HashMap<String, Object>();
		// User Data
		Map<String, Object> userData = new HashMap<String, Object>();
		userData.put("name", "Vineet");
		userData.put("email", "vinzee93@gmail.com");
		userData.put("address", "Maryland");
		userData.put("city", "Baltimore");
		userData.put("country", "United States");
		userData.put("postalCode", "21227");
		resource.put("user", userData);

		// Wallet Data
		Map<String, Object> walletData = new HashMap<String, Object>();
		walletData.put("balance", 123);
		resource.put("virtualWallet", walletData);

		// Bank Data
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

		List<Map<String, Object>> paymentMethodss = new ArrayList<Map<String, Object>>();
		paymentMethodss.add(paymentMathodOne);
		paymentMethodss.add(paymentMethodTwo);
		resource.put("paymentMethods", paymentMethodss);

		// Investment Data
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

		List<Map<String, Object>> investments = new ArrayList<Map<String, Object>>();
		investments.add(investOne);
		investments.add(investTwo);
		investments.add(investThree);
		investments.add(investFour);
		resource.put("investments", investments);

		// Transaction Data
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

		List<Map<String, Object>> transactions = new ArrayList<Map<String, Object>>();
		transactions.add(transOne);
		transactions.add(transTwo);
		transactions.add(transThree);
		transactions.add(transFour);
		resource.put("transactions", transactions);
		return resource;
	}

	// private UserProfileView getDummyProfile() {
	// return UserProfileView.getDummyUserProfile();
	// }

	public UserProfileView getUserProfileView(String email) throws CryptxException {
		UserProfileView userProfile = new UserProfileView();

		CryptxUser user = userService.findUserByEmail(email);
		int userId = user.getUserId();

		userProfile.setUser(user);
		try {
			userProfile.setTransactions(transactionService.getUserTransaction(userId));
		} catch (CryptxException e) {
			userProfile.setTransactions(new ArrayList<Transaction>());
		}
		try {
			userProfile.setVirtualWallet(virtualWalletService.getUserVirtualWallet(userId));
		} catch (CryptxException e) {
			userProfile.setVirtualWallet(new VirtualWallet());
		}
		try {
			userProfile.setPortfolio(portfolioService.getUserPortfolio(userId));
		} catch (CryptxException e) {
			userProfile.setPortfolio(new Portfolio());
		}
		// userProfile.setInvestments(investments);
		// userProfile.setCardDetails(cardDetails);
		try {
			userProfile.setPaymentMethods(paymentMethodService.getUserPaymentMethodList(userId));
		} catch (Exception e) {
			userProfile.setPaymentMethods(null);
		}

		return userProfile;
	}

}
