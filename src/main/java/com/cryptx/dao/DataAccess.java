package com.cryptx.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cryptx.utils.ConnectionManager;

@Service(IDataAccess.DATA_ACCESS_SERVICE)
public class DataAccess implements IDataAccess {

	private static final Logger logger = LoggerFactory.getLogger(DataAccess.class);

	private static Connection connection = ConnectionManager.getConnection();

	@Override
	public ResultSet executeQuery(String queryString) throws SQLException {
		ResultSet rs = null;
		Statement stmt = connection.createStatement();
		logger.info(String.format("Executing Query: [%s]", queryString));
		rs = stmt.executeQuery(queryString);
		return rs;
	}
}
