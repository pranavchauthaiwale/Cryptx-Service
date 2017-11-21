package com.cryptx.services;

import com.cryptx.models.CryptxUser;

public interface IUserService {

	public static final String USER_SERVICE = "UserService";
	void createNewUser(CryptxUser userView);
	CryptxUser findUserByEmail(String email);

}
