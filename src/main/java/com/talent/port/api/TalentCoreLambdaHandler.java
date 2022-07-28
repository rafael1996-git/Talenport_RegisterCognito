package com.talent.port.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.WebApplicationInitializer;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class TalentCoreLambdaHandler implements RequestStreamHandler{

	 private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
	 public TalentCoreLambdaHandler(){

	    }
	  public static void initHandler(Class<? extends WebApplicationInitializer> applicationClass) throws Exception {
	        try {
	            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(applicationClass);
	        } catch (ContainerInitializationException var3) {
	            String errMsg = "Could not initialize Spring Boot application";
	            System.out.println(errMsg);
	            throw new RuntimeException("Could not initialize Spring Boot application", var3);
	        }
	    }
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		handler.proxyStream(input, output, context);
		output.close();
	}
	

}
