package com.cryptx.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CryptxRestController {

	private static final Logger logger = LoggerFactory.getLogger(CryptxRestController.class);

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public Map<String, String> loginUser() {
		logger.info("Logging user In");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Login user Resource");
		return resource;
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpSession session) {
		logger.info("Loggin-out User. Invalidating Session");
		session.invalidate();
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public Map<String, String> signup() {
		logger.info("Registering new user in cryptx");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Register User Resource");
		return resource;
	}

	@RequestMapping(value = "getbankdetails", method = RequestMethod.GET)
	public Map<String, String> getBankDetails() {
		logger.info("Request for bank details of user");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Get Bank Details Resource");
		return resource;
	}

	@RequestMapping(value = "addbankdetails", method = RequestMethod.POST)
	public Map<String, String> addBankDetails() {
		logger.info("Request for adding bank details for user");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Add bank Details Resource");
		return resource;
	}

	@RequestMapping(value = "updatebankdetails", method = RequestMethod.POST)
	public Map<String, String> updateBankDetails() {
		logger.info("Requesting for updating bank details of user");
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "Resource for updating bank details of user");
		return resource;
	}
	
	@RequestMapping(value="currencyhistory/{currency}", method=RequestMethod.GET)
	public Map<String, String> getCurrencyHistory(@PathVariable String currency) {
		logger.info("Requesting historic data of currency [" + currency + "]");
		Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "Historic Currency Data Resource");
        return resource;
	}
	
	@RequestMapping(value="getvirtualwalletdetails", method=RequestMethod.GET)
	public Map<String, String> getUserVirtualWallet() {
		logger.info("Requesting virtual wallet information of user");
		Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "Virtual Wallet Resource of user");
        return resource;
	}
	
	@RequestMapping(value="addvirtualwallet", method=RequestMethod.POST)
	public Map<String, String> addVirtualWallet() {
		logger.info("Requesting for adding new virtual wallet for user");
		Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "Add Virtual Wallet Resource");
        return resource;
	}
	
	@RequestMapping(value="updatevirtualwallet", method=RequestMethod.POST)
	public Map<String, String> updateVirtualWalletDetails() {
		logger.info("Requesting for updating virtual wallet information of user");
		Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "Update Virtual Wallet Resource");
        return resource;
	}
	
	@RequestMapping(value="buy", method=RequestMethod.POST)
	public Map<String, String> buyCurrency() {
		logger.info("Requesting for buying specified currency");
		Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "Buy Currency Resource");
        return resource;
	}
	
	@RequestMapping(value="sell", method=RequestMethod.POST)
	public Map<String, String> sell() {
		logger.info("Requesting for selling specified currencyr");
		Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "Sell Currency Resource");
        return resource;
	}
}
