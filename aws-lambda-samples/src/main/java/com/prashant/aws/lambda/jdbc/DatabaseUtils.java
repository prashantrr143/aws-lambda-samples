package com.prashant.aws.lambda.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Database Connection Utility
 * 
 * @author prasingh26
 *
 */
public class DatabaseUtils {

	public static final Logger LOGGER = LogManager.getLogger(DatabaseUtils.class);

	private DatabaseUtils() {
	}

	/**
	 * Method to get Connection from the configuration provided as the parameters
	 * 
	 * @param jdbcUrl  JDBC URL
	 * @param user     DB User Name
	 * @param password DB Password
	 * @param database DB Name
	 * @return
	 */
	public static Connection getConnection(String jdbcUrl, String user, String password, String portNumber,
			String databaseName) {
		LOGGER.info("getConnection method called : jdbcUrl " + jdbcUrl);
		Connection con = null;
		try {
			// registering driver
			DriverManager.registerDriver(new org.postgresql.Driver());
			String formulatedJDBCUrl = createActualJDBCUrl(jdbcUrl, portNumber, databaseName);
			LOGGER.info("Actual JDBC URL formulated is " + formulatedJDBCUrl);
			con = DriverManager.getConnection(formulatedJDBCUrl, user, password);
			con.setAutoCommit(false);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		LOGGER.info("Returning Connection instance ? " + con != null ? "Yes" : "No");

		return con;
	}

	/**
	 * Method to create the actual JDBC URL string
	 * 
	 * @param dbLocation
	 * @param portNumber
	 * @param databaseName
	 * @return
	 */
	private static String createActualJDBCUrl(String dbLocation, String portNumber, String databaseName) {
		// jdbc:postgresql://127.0.0.1:5432/spring-batch
		StringBuilder jdbcUrlBuilder = new StringBuilder("jdbc");
		jdbcUrlBuilder.append(":").append("postgresql://").append(dbLocation).append(":").append(portNumber).append("/")
				.append(databaseName);
		return jdbcUrlBuilder.toString();

	}

	/**
	 * Close the connection created
	 * 
	 * @param con
	 */
	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				if (!con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Error while closing the connection : " + e.getMessage());
				e.printStackTrace();
			}

		}

	}

}
