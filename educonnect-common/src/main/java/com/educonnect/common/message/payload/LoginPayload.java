package com.educonnect.common.message.payload;

import java.util.Arrays;

import com.educonnect.common.client.ClientType;

/**
 * A LoginPayload is a concrete implementation of a {@link Payload}. It contains 
 * all the required fields required for logging a user in and specifying what 
 * kind of user is logging in.
 *   
 * @author Sensei
 *
 */
public class LoginPayload extends Payload {
	
	private String     emailId    = null;
	private char[]     password   = null;
	private ClientType clientType = null;

	/**
	 * The standard constructor for creating a LoginPayload
	 * 
	 * @param emailId    The eMail id of the user
	 * @param password   The password of the user 
	 * @param clientType The type of user logging in - can either be an ADMIN or 
	 * a STUDENT
	 */
	public LoginPayload( String emailId, char[] password, ClientType clientType ) {
		this.emailId    = emailId;
		this.password   = password;
		this.clientType = clientType;
	}

	public String getEmailId() {
		return emailId;
	}
	
	public char[] getPassword() {
		return password;
	}
	
	public ClientType getClientType() {
		return clientType;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == this ) return true;
		if( obj == null ) return false;
		if( obj.getClass() == this.getClass() && obj.hashCode() == this.hashCode() ) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		
		int emailHash       = emailId     == null  ? 7  : emailId.hashCode();
		int passwordHash    = password    == null  ? 11 : Arrays.hashCode( password );
		int clientTypeHash  = clientType  == null  ? 13 : clientType.hashCode();
		
		return emailHash + passwordHash + clientTypeHash;
	}
}
