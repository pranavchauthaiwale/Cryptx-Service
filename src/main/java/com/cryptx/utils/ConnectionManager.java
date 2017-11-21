package com.cryptx.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static String CONNECTION_DRIVER = "jdbc:mariadb://localhost/cryptxmaster";
	private static String USERNAME = "root";
	private static String PASSWORD = "admin";
	private static Connection connection;

	private ConnectionManager() {}

	public static Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(CONNECTION_DRIVER, USERNAME, PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}
