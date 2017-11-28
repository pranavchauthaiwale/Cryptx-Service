package com.cryptx.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptx.dao.IDataAccess;
import com.cryptx.exception.CryptxException;
import com.cryptx.models.CryptxUser;

@Service(IUserService.USER_SERVICE)
public class UserService implements IUserService {

	@Autowired
	IDataAccess dataAccess;

	@Override
	public void createNewUser(CryptxUser userView) throws CryptxException {
		String query = String.format(
				"INSERT INTO user (name, email, phone, password, ssn,address, city, country, postalcode) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
				userView.getName(), userView.getEmail(), userView.getPhone(), userView.getPassword(), userView.getSsn(),
				userView.getAddress(), userView.getCity(), userView.getCountry(), userView.getPostalCode());

		ResultSet rs = dataAccess.executeQuery(query);
		if(rs == null) {
			throw new CryptxException("Error in creating new user");
		}
	}

	@Override
	public CryptxUser findUserByEmail(String email) {
		String query = String.format("Select * FROM user where email='%s'", email);

		ResultSet rs = dataAccess.executeQuery(query);
		try {
			if(!rs.isBeforeFirst()) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return buildUserFromResultSet(rs);
	}
	
	private CryptxUser buildUserFromResultSet(ResultSet resultSet) {
		
		CryptxUser user = new CryptxUser();
		try {
			while(resultSet.next()) {
				user.setName(resultSet.getString(1));
				user.setEmail(resultSet.getString(2));
				user.setPhone(resultSet.getString(3));
				user.setPassword(resultSet.getString(4));
				user.setSsn(resultSet.getString(5));
				user.setAddress(resultSet.getString(6));
				user.setCity(resultSet.getString(7));
				user.setCountry(resultSet.getString(8));
				user.setPostalCode(resultSet.getString(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	

}
