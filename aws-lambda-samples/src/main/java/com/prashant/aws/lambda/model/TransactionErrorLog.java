package com.prashant.aws.lambda.model;

import java.sql.Timestamp;

public class TransactionErrorLog {

	private long trasnactionId;
	private String error;
	private String userId;
	private Timestamp createDt;

	public long getTrasnactionId() {
		return trasnactionId;
	}

	public void setTrasnactionId(long trasnactionId) {
		this.trasnactionId = trasnactionId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}

	@Override
	public String toString() {
		return "TransactionErrorLog [trasnactionId=" + trasnactionId + ", error=" + error + ", userId=" + userId
				+ ", createDt=" + createDt + "]";
	}

}
