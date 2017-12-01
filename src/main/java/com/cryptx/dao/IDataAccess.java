package com.cryptx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDataAccess {
	
	public static final String DATA_ACCESS_SERVICE = "DataAccessService";
	ResultSet executeQuery(String queryString) throws SQLException;

}
