package com.cryptx.dao;

import java.sql.ResultSet;

public interface IDataAccess {
	
	public static final String DATA_ACCESS_SERVICE = "DataAccessService";
	ResultSet executeQuery(String queryString);

}
