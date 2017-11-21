package com.cryptx.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CryptxRestController {

	private static final Logger logger = LoggerFactory.getLogger(CryptxRestController.class);
	
	@RequestMapping(value="in", method=RequestMethod.GET)
	public Map<String, String> loginUser() {
		logger.info("Logging user In");
		Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "Login user Resource");
        return resource;
	}
	
	@RequestMapping(value = "out", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
		logger.info("Loggin-out User. Invalidating Session");
        session.invalidate();
    }
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public Map<String, String> signup() {
		logger.info("Registering new user in cryptx");
		Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "Register User Resource");
        return resource;
	}
}
