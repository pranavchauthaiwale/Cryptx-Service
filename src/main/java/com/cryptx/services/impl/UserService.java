package com.cryptx.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptx.dao.IDataAccess;
import com.cryptx.exception.CryptxException;
import com.cryptx.models.CryptxUser;
import com.cryptx.services.IUserService;

@Service(IUserService.USER_SERVICE)
public class UserService implements IUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	IDataAccess dataAccess;

	@Override
	public void createNewUser(CryptxUser userView) throws CryptxException {
		String query = String.format(
				"INSERT INTO user (name, email, phone, password, ssn,address, city, country, postalcode) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
				userView.getName(), userView.getEmail(), userView.getPhone(), userView.getPassword(), userView.getSsn(),
				userView.getAddress(), userView.getCity(), userView.getCountry(), userView.getPostalCode());
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in creating new user");
		}
	}

	@Override
	public CryptxUser findUserByEmail(String email) throws CryptxException {
		String query = String.format("Select * FROM user where email='%s'", email);

		ResultSet rs;
		try {
			rs = dataAccess.executeQuery(query);
			if (!rs.isBeforeFirst()) {
				throw new CryptxException("Resultset Empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return buildUserFromResultSet(rs);
	}

	private CryptxUser buildUserFromResultSet(ResultSet resultSet) throws CryptxException {

		CryptxUser user = new CryptxUser();
		try {
			while (resultSet.next()) {
				user.setName(resultSet.getString("name"));
				user.setEmail(resultSet.getString("email"));
				user.setPhone(resultSet.getString("phone"));
				user.setPassword(resultSet.getString("password"));
				user.setSsn(resultSet.getString("ssn"));
				user.setAddress(resultSet.getString("address"));
				user.setCity(resultSet.getString("city"));
				user.setCountry(resultSet.getString("country"));
				user.setPostalCode(resultSet.getString("postalcode"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return user;
	}

}
