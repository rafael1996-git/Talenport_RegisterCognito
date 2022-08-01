package com.talent.port.api;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InternalServiceErrorException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talent.port.api.services.IUserService;
import com.talent.port.api.utils.SpringJdbcTemplateConfig;

public class SecretManager {
	

	// Use this code snippet in your app.
	// If you need more information about configurations or implementing the sample code, visit the AWS docs:
	// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-samples.html#prerequisites
    private static final Logger log = LoggerFactory.getLogger(SecretManager.class);
   
    
	public static   String getSecret(String conecta) throws Exception {

	    String secretName = "com/talentport/backend";
	    String region = "us-east-1";

	    // Create a Secrets Manager client
	    AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
	                                    .withRegion(region)
	                                    .build();
	    
	    // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
	    // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
	    // We rethrow the exception by default.
	    
	    String secret, decodedBinarySecret;
	    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
	                    .withSecretId(conecta);
	    GetSecretValueResult getSecretValueResult = null;

	    try {
	        getSecretValueResult = client.getSecretValue(getSecretValueRequest);
	    } catch (DecryptionFailureException e) {
	        // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    } catch (InternalServiceErrorException e) {
	        // An error occurred on the server side.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    } catch (InvalidParameterException e) {
	        // You provided an invalid value for a parameter.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    } catch (InvalidRequestException e) {
	        // You provided a parameter value that is not valid for the current state of the resource.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    } catch (ResourceNotFoundException e) {
	        // We can't find the resource that you asked for.
	        // Deal with the exception here, and/or rethrow at your discretion.
	        throw e;
	    }

	    // Decrypts secret using the associated KMS key.
	    // Depending on whether the secret is a string or binary, one of these fields will be populated.
	    if (getSecretValueResult.getSecretString() != null) {
//	        secret = getSecretValueResult.getSecretString();
//	        
//            ObjectMapper objectMapper  =  new  ObjectMapper();
//            String secretValue= client.getSecretValue(getSecretValueRequest).getSecretString();
//            JsonNode secretsJson  =  objectMapper.readTree(secretValue);
//             String  host  =  secretsJson.get("host").textValue();
//            String  port  =  secretsJson.get("port").textValue();
//            String  dbname  =  secretsJson.get("dbname").textValue();
//            String  username  =  secretsJson.get("username").textValue();
//            String  password  =  secretsJson.get("password").textValue();
//            SpringJdbcTemplateConfig opj=new SpringJdbcTemplateConfig();
            return getSecretValueResult.getSecretString();
//            opj.getConexion(dbname, username, password);
	    }
	    else {
	        decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
	    }
		return decodedBinarySecret;

	    // Your code goes here.
	}
}
