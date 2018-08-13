package com.prashant.aws.lambda;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.prashant.aws.lambda.dao.TransactionErrorLogDao;
import com.prashant.aws.lambda.dao.impl.TransactionErrorLogDaoImpl;
import com.prashant.aws.lambda.http.HttpInvokerRequestHandler;
import com.prashant.aws.lambda.model.TransactionErrorLog;

public class OrderServiceRequestHandler implements RequestHandler<Object, String> {
	
	
	public static Logger LOGGER = LogManager.getLogger(OrderServiceRequestHandler.class);
	
	private TransactionErrorLogDao errorDao = new TransactionErrorLogDaoImpl();


    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + System.getProperty("java.classpath"));
        
        for(int i =0 ; i < 5; i++) {
        	LOGGER.info("Logged by Logger to cloudwatch " + "Prashant "+ new java.util.Date());
        }
        
        String baseUri = "http://services.groupkt.com/state/get/IND/all";

		LOGGER.info("Invoking a get Request at URI " + baseUri);
		HttpInvokerRequestHandler httpInvokerRequestHander = new HttpInvokerRequestHandler();

		try {

			String response = httpInvokerRequestHander.callExternalService("GET", baseUri);
			if(response != null) {
				LOGGER.info("Web sevice returned a result ? "  + response != null ?"true" :"false");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	/*	for (int i = 0; i < 5; i++) {
			TransactionErrorLog log = new TransactionErrorLog();
			log.setError("Customized Error Log for Iteration Count  " + (i + 1));
			log.setUserId("prashant_singh");
			log.setCreateDt(new Timestamp(new Date().getTime()));
			errorDao.save(log);
		}*/

		List<TransactionErrorLog> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			TransactionErrorLog log = new TransactionErrorLog();
			log.setError("Customized Error Log for Iteration Count  " + (i + 1));
			log.setUserId("prashant_singh");
			log.setCreateDt(new Timestamp(new Date().getTime()));
			list.add(log);
			//errorDao.save(log);
		}

		int[] status = errorDao.save(list);

		LOGGER.info("Done with savin data to AWS RDS Instance");
		LOGGER.info("Fetching Data from RDS Instance");

		List<TransactionErrorLog> errorLogList = errorDao.getErrorLog(new Timestamp(new Date().getTime()));
		errorLogList.forEach(e -> {
			LOGGER.info("Retrieved from Database : " + e);
		});

		LOGGER.info("Function name: " + context.getFunctionName());
		LOGGER.info("Max mem allocated: " + context.getMemoryLimitInMB());
		LOGGER.info("Time remaining in milliseconds: " + context.getRemainingTimeInMillis());
		LOGGER.info("CloudWatch log stream name: " + context.getLogStreamName());
		LOGGER.info("CloudWatch log group name: " + context.getLogGroupName());

		LOGGER.info("Exiting Method handleRequest");
        

        // TODO: implement your handler
        return "Hello from Lambda!";
    }

}
