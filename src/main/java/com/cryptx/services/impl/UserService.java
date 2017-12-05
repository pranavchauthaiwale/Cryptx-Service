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
				userView.getName(), userView.getEmail().toLowerCase(), userView.getPhone(), userView.getPassword().toLowerCase(), userView.getSsn(),
				userView.getAddress(), userView.getCity(), userView.getCountry(), userView.getPostalCode());
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in creating new user");
		}
	}

	@Override
	public CryptxUser updateUserDetails(CryptxUser updatedUser, String userEmail) throws CryptxException {

		if (!updatedUser.getEmail().equalsIgnoreCase(userEmail)) {
			throw new CryptxException("Email cannot be modified");
		}
		
		CryptxUser currentUser = findUserByEmail(userEmail);
		String query = String.format(
				"UPDATE user SET name = '%s', ssn = '%s', phone = '%s', address = '%s', city = '%s', country = '%s', postalcode = '%s' WHERE userid = %d",
				updatedUser.getName(), updatedUser.getSsn(), updatedUser.getPhone(), updatedUser.getAddress(),
				updatedUser.getCity(), updatedUser.getCountry(), updatedUser.getPostalCode(), currentUser.getUserId());
		
		try {
			dataAccess.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("Error in creating new user");
		}
		currentUser = findUserByEmail(userEmail);
		return currentUser;
	}

	@Override
	public CryptxUser findUserAuthDetailsByEmail(String email) throws CryptxException {
		String query = String.format("Select email, password FROM user where email='%s'", email);

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
		return buildUserAuthObject(rs);
	}

	private CryptxUser buildUserAuthObject(ResultSet resultSet) throws CryptxException {
		CryptxUser user = new CryptxUser();
		try {
			while (resultSet.next()) {
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CryptxException("SQL Exception. Error: " + e.getMessage());
		}
		return user;
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
				user.setUserId(resultSet.getInt("userid"));
				user.setName(resultSet.getString("name"));
				user.setEmail(resultSet.getString("email"));
				user.setPhone(resultSet.getString("phone"));
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
