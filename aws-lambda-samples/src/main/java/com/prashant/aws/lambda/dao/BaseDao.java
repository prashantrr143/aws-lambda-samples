package com.prashant.aws.lambda.dao;

import java.sql.Connection;

public interface BaseDao {
	
	

	public Connection getConnection();

	public void closeConnection(Connection con);
}
