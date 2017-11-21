package com.cryptx.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import com.cryptx.utils.ConnectionManager;

@Service(IDataAccess.DATA_ACCESS_SERVICE)
public class DataAccess implements IDataAccess {

	private static Connection connection = ConnectionManager.getConnection();

	@Override
	public ResultSet executeQuery(String queryString) {
		ResultSet rs = null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(queryString);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return rs;
	}
}
