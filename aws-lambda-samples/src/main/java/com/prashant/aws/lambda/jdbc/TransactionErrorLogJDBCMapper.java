package com.prashant.aws.lambda.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.prashant.aws.lambda.model.TransactionErrorLog;



/**
 * Mapper Implementation to map an instance of ResultSet to TransactionErrorLog
 * 
 * @author prasingh26
 *
 */
public class TransactionErrorLogJDBCMapper implements JDBCMapper<TransactionErrorLog> {

	/**
	 * Map a ResultSet to an instance of TransactonErrorLog
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	@Override
	public TransactionErrorLog map(ResultSet rs) throws SQLException {
		TransactionErrorLog transactionErrorLog = new TransactionErrorLog();
		transactionErrorLog.setTrasnactionId(rs.getLong(1));
		transactionErrorLog.setError(rs.getString(2));
		transactionErrorLog.setUserId(rs.getString(3));
		transactionErrorLog.setCreateDt(rs.getTimestamp(4));
		return transactionErrorLog;

	}
}
