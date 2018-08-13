package com.prashant.aws.lambda.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.prashant.aws.lambda.model.TransactionErrorLog;



public interface TransactionErrorLogDao {

	public Serializable save(TransactionErrorLog erroLog);
	
	public int[] save(List<TransactionErrorLog> errorLogs);

	public List<TransactionErrorLog> getErrorLog(Timestamp createDt);

}
