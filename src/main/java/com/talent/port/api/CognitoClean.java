package com.talent.port.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminSetUserPasswordRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.MessageActionType;

public class CognitoClean {
	
	private static final String ACCESS_KEY = "AKIAXHK4TJMWXPLW5SWU";
	private static final String SECRET_KEY = "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";
	private static final String userPoolId = "us-east-1_f5HmZlNXE";
	private static final Logger log = LoggerFactory.getLogger(CognitoClean.class);

	public void CognitoAws (String email, String password) throws Exception{
		//AWS credentials
		

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

		AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder
		        .standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
		        .withRegion("us-east-1").build();

//		String username = "juan";
		
		try {
		    AttributeType emailAttr = new AttributeType().withName("email").withValue(email);
		    AttributeType emailVerifiedAttr =
		            new AttributeType().withName("email_verified").withValue("false");

		    AdminCreateUserRequest userRequest =
		            new AdminCreateUserRequest().withUserPoolId(userPoolId).withUsername(email)
		                    .withTemporaryPassword(password)
		                    .withUserAttributes(emailAttr, emailVerifiedAttr)
		                    .withMessageAction(MessageActionType.SUPPRESS);

		    AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(userRequest);

		    System.out.println("User " + createUserResult.getUser().getUsername()
		            + " is created. Status: " + createUserResult.getUser().getUserStatus());

		    // Make the password permanent and not temporary
		    AdminSetUserPasswordRequest adminSetUserPasswordRequest =
		            new AdminSetUserPasswordRequest().withUsername(email)
		                    .withUserPoolId(userPoolId).withPassword(password).withPermanent(true);
		    cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);
		} catch (AWSCognitoIdentityProviderException e) {
		    System.out.println(e.getErrorMessage());
		} catch (Exception e) {
		    System.out.println(e);
		}
		}

}
