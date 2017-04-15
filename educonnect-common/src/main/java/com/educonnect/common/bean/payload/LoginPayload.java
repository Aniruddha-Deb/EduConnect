package com.educonnect.common.bean.payload;

import com.educonnect.common.client.ClientType;

/**
 * A LoginPayload is a concrete implementation of a {@link Payload}. It contains 
 * all the required fields required for logging a user in.  
 * @author Sensei
 *
 */
public class LoginPayload extends Payload {
	
	private String     emailId    = null;
	private String     password   = null;
	private ClientType clientType = null;
	/**
	 * The standard constructor for creating a LoginPayload 
	 * 
	 * @param grade   The grade or class the student belongs to
	 * @param section The section the student belongs to
	 * @param rollNo  The roll number of the student in that class
	 */
	public LoginPayload( String emailId, String password, ClientType clientType ) {
		this.emailId    = emailId;
		this.password   = password;
		this.clientType = clientType;
	}

	public String getEmailId() {
		return emailId;
	}
	
	public String getPassword() {
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
		int passwordHash    = password    == null  ? 11 : password.hashCode();
		int clientTypeHash  = clientType  == null  ? 13 : clientType.hashCode();
		
		return emailHash + passwordHash + clientTypeHash;
	}
}
