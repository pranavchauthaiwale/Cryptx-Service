package com.cryptx.controllers;

import java.util.HashMap;
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
import com.cryptx.services.IUserService;

@CrossOrigin
@RestController
@RequestMapping("/")
public class CryptxRestController {

	@Autowired
	IUserService userService;

	private static final Logger logger = LoggerFactory.getLogger(CryptxRestController.class);

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<?> loginUser() {
		logger.info("Logging user In");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Login user Resource");
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpSession session) {
		logger.info("Loggin-out User. Invalidating Session");
		session.invalidate();
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ResponseEntity<?> signup(@RequestBody CryptxUser userView) {
		logger.info("Registering new user in cryptx");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			userService.createNewUser(userView);
			resource.put("message", "User Created Successfully");
		} catch (CryptxException e) {
			e.printStackTrace();
			resource.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			resource.put("message", "Registration of New User Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "user/payment_details", method = RequestMethod.GET)
	public ResponseEntity<?> getPaymentDetails() {
		logger.info("Request for bank details of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put("message", "Payment Details");
		} catch (Exception e) {
			e.printStackTrace();
			resource.put("message", "Payment Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "payment_method/create", method = RequestMethod.POST)
	public ResponseEntity<?> addPaymentDetails() {
		logger.info("Request for adding bank details for user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put("message", "Bank Details Added Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			resource.put("message", "Add Bank Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "payment_method/update", method = RequestMethod.POST)
	public ResponseEntity<?> updatePaymentDetails() {
		logger.info("Requesting for updating bank details of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put("message", "Payment Details Updated Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			resource.put("message", "Update Payment Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "payment_method/delete", method = RequestMethod.POST)
	public ResponseEntity<?> deletePaymentDetails() {
		logger.info("Deleting bank details of user");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put("message", "Payment Details Deleted Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			resource.put("message", "Delete Payment Details Request Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "currencyhistory/{currency}", method = RequestMethod.GET)
	public ResponseEntity<?> getCurrencyHistory(@PathVariable String currency) {
		logger.info("Requesting historic data of currency [" + currency + "]");
		Map<String, Object> resource = new HashMap<String, Object>();
		try {
			resource.put("message", "Historic Currency Details");
		} catch (Exception e) {
			e.printStackTrace();
			resource.put("message", "Request Historical Currency Trends Failed Unexpectedly");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = "wallet/details", method = RequestMethod.GET)
	public Map<String, String> getUserVirtualWalletDetails() {
		logger.info("Requesting virtual wallet information of user");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Virtual Wallet Resource of user");
		return resource;
	}

	// @RequestMapping(value="addvirtualwallet", method=RequestMethod.POST)
	// public Map<String, String> addVirtualWallet() {
	// logger.info("Requesting for adding new virtual wallet for user");
	// Map<String, String> resource = new HashMap<String, String>();
	// resource.put("resource", "Add Virtual Wallet Resource");
	// return resource;
	// }

	@RequestMapping(value = "wallet/deposit", method = RequestMethod.POST)
	public Map<String, String> depositInVirtualWallet() {
		logger.info("Requesting for depositing in virtual wallet of user");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Deposit Virtual Wallet Resource");
		return resource;
	}

	@RequestMapping(value = "wallet/withdraw", method = RequestMethod.POST)
	public Map<String, String> withdrawFromVirtualWallet() {
		logger.info("Requesting for withdrawing from virtual wallet of user");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Withdraw Virtual Wallet Resource");
		return resource;
	}

	@RequestMapping(value = "currency/buy", method = RequestMethod.POST)
	public Map<String, String> buyCurrency() {
		logger.info("Requesting for buying specified currency");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Buy Currency Resource");
		return resource;
	}

	@RequestMapping(value = "currency/sell", method = RequestMethod.POST)
	public Map<String, String> sell() {
		logger.info("Requesting for selling specified currencyr");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Sell Currency Resource");
		return resource;
	}

	@RequestMapping(value = "user/profile", method = RequestMethod.GET)
	public ResponseEntity<?> getUserProfile() {
		logger.info("Retrieving user profile informations");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Sell Currency Resource");
		return new ResponseEntity<Map<String, String>>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "user/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserProfile() {
		logger.info("Updating user profile informations");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Sell Currency Resource");
		return new ResponseEntity<Map<String, String>>(resource, HttpStatus.OK);
	}
}
