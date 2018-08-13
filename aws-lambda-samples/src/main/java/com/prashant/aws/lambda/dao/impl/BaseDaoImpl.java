package com.prashant.aws.lambda.dao.impl;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.prashant.aws.lambda.dao.BaseDao;
import com.prashant.aws.lambda.jdbc.DatabaseUtils;



public class BaseDaoImpl implements BaseDao {

	public static final Logger LOGGER = LogManager.getLogger(BaseDaoImpl.class);
	private static Properties dbConfigProperties = new Properties();
	static {

//		ResourceBundle rb = ResourceBundle.getBundle("lambda-configuration");
//		Enumeration<String> keys = rb.getKeys();
//		while (keys.hasMoreElements()) {
//			String key = keys.nextElement();
//			dbConfigProperties.put(key, rb.getString(key));
//		}
		
		dbConfigProperties.put("jdbc_aws_rds_uri", "aws-***************.amazonaws.com");
		dbConfigProperties.put("jdbc_rds_user", "prashant");
		dbConfigProperties.put("jdbc_rds_port", "***");
		dbConfigProperties.put("jdbc_rds_dbName", "****");
		dbConfigProperties.put("jdbc_rds_password", "****");
		
		
		LOGGER.info("Total Properties configured is : " + dbConfigProperties.size());

	}

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return DatabaseUtils.getConnection(dbConfigProperties.getProperty("jdbc_aws_rds_uri"),
				dbConfigProperties.getProperty("jdbc_rds_user"), dbConfigProperties.getProperty("jdbc_rds_password"),
				dbConfigProperties.getProperty("jdbc_rds_port"), dbConfigProperties.getProperty("jdbc_rds_dbName"));
	}

	@Override
	public void closeConnection(Connection con) {
		DatabaseUtils.closeConnection(con);
	}

}
