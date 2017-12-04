package com.cryptx.services;

import com.cryptx.exception.CryptxException;
import com.cryptx.models.CryptxUser;

public interface IUserService {

	public static final String USER_SERVICE = "UserService";

	void createNewUser(CryptxUser userView) throws CryptxException;

	CryptxUser findUserByEmail(String email) throws CryptxException;

	CryptxUser findUserAuthDetailsByEmail(String email) throws CryptxException;

	CryptxUser updateUserDetails(CryptxUser updatedUser, String userEmail) throws CryptxException;

}
