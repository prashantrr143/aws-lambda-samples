package com.prashant.aws.lambda.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.prashant.aws.lambda.dao.TransactionErrorLogDao;
import com.prashant.aws.lambda.jdbc.TransactionErrorLogJDBCMapper;
import com.prashant.aws.lambda.model.TransactionErrorLog;

public class TransactionErrorLogDaoImpl extends BaseDaoImpl implements TransactionErrorLogDao {

	private static enum TRANSACTION_NAMED_QUERY {

		INSERT("INSERT INTO TRANSACTION_ERROR_LOG (ERROR,USER_ID,CREATE_DT) VALUES (?,?,?)"),
		SELECT("SELECT * FROM TRANSACTION_ERROR_LOG WHERE CREATE_DT < ?");

		private String query;

		TRANSACTION_NAMED_QUERY(String query) {
			this.query = query;
		}

		public String getQuery() {
			return this.query;
		}

	}

	@Override
	public Serializable save(TransactionErrorLog erroLog) {
		LOGGER.info("ABOUT TO EXECUTE : save " + erroLog);
		Connection con = getConnection();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(TRANSACTION_NAMED_QUERY.INSERT.getQuery());
			ps.setString(1, erroLog.getError());
			ps.setString(2, erroLog.getUserId());
			ps.setTimestamp(3, erroLog.getCreateDt());
			LOGGER.info("ABOUT TO EXECUTE : " + ps);
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error commiting the statement :" + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOGGER.error("Error rollback :" + e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}

		return null;
	}

	@Override
	public int[] save(List<TransactionErrorLog> errorLogs) {
		LOGGER.info("ABOUT TO EXECUTE : save List of Transaction Error Logs ");
		Connection con = getConnection();
		PreparedStatement ps;
		int[] statusUpdate = null;
		try {
			ps = con.prepareStatement(TRANSACTION_NAMED_QUERY.INSERT.getQuery());
			errorLogs.forEach(

					e -> {
						try {
							ps.setString(1, e.getError());
							ps.setString(2, e.getUserId());
							ps.setTimestamp(3, e.getCreateDt());
							ps.addBatch();
						} catch (SQLException se) {
							se.printStackTrace();
						}
					}

			);

			LOGGER.info("ABOUT TO EXECUTE : " + ps);
			statusUpdate = ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error commiting the statement :" + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOGGER.error("Error rollback :" + e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}

		return statusUpdate;
	}

	@Override
	public List<TransactionErrorLog> getErrorLog(Timestamp createDt) {
		Connection con = getConnection();
		PreparedStatement ps;
		List<TransactionErrorLog> errorLogList = new ArrayList<>();
		try {
			ps = con.prepareStatement(TRANSACTION_NAMED_QUERY.SELECT.getQuery());
			ps.setTimestamp(1, createDt);
			LOGGER.info("ABOUT TO EXECUTE : " + ps);
			ResultSet rs = ps.executeQuery();
			TransactionErrorLogJDBCMapper mapper = new TransactionErrorLogJDBCMapper();
			while (rs.next()) {
				errorLogList.add(mapper.map(rs));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error commiting the statement :" + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOGGER.error("Error rollback :" + e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}

		return errorLogList;

	}

}
