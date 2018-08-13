package com.prashant.aws.lambda.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpInvokerRequestHandler {

	public static final Logger LOGGER = LogManager.getLogger(HttpInvokerRequestHandler.class);
	private HttpClient httpClient = HttpClients.createDefault();

	public String callExternalService(String methodName, String uri) throws IOException {
		LOGGER.info("Entering call to callExternalService ");
		HttpUriRequest request = new HttpGet(uri);

		try {
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					LOGGER.info(" Request Status is " + status);

					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
						
					}
				}

			};

			String responseBody = httpClient.execute(request, responseHandler);
			return responseBody;

		} finally {
			LOGGER.info("Closing HttpClient ");
			((CloseableHttpClient) httpClient).close();
		}
		
		

	}

}
