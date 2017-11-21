package com.cryptx.services;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptx.dao.IDataAccess;
import com.cryptx.models.CryptxUser;

@Service(IUserService.USER_SERVICE)
public class UserService implements IUserService {

	@Autowired
	IDataAccess dataAccess;

	@Override
	public void createNewUser(CryptxUser userView) {
		String query = String.format(
				"INSERT INTO user (name, email, phone, password, ssn,address, city, country, postalcode) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
				userView.getName(), userView.getEmail(), userView.getPhone(), userView.getPassword(), userView.getSsn(),
				userView.getAddress(), userView.getCity(), userView.getCountry(), userView.getPostalCode());

		dataAccess.executeQuery(query);

	}

	@Override
	public CryptxUser findUserByEmail(String email) {
		String query = String.format("Select * FROM user where email='%s'", email);

		ResultSet rs = dataAccess.executeQuery(query);
		return null;
	}

}
