package com.educonnect.common.bean.payload;

/**
 * An AuthPayload is a concrete implementation of a {@link Payload}. It contains 
 * all the required fields required for authenticating a user.  
 * @author Sensei
 *
 */
public class AuthPayload extends Payload {
	
	private String emailId = null;
	private String passwd  = null;
	
	/**
	 * The standard constructor for creating an AuthPayload
	 * @param emailId The emailId of the user to be authenticated
	 * @param passwd The password of the user to be authenticated
	 */
	public AuthPayload( String emailId, String passwd ) {
		this.emailId = emailId;
		this.passwd  = passwd;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public String getPasswd() {
		return passwd;
	}
}
