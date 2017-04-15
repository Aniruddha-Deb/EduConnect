package com.educonnect.common.bean.payload;

/**
 * An AuthPayload is a concrete implementation of a {@link Payload}. It contains 
 * all the required fields required for authenticating a user.  
 * @author Sensei
 *
 */
public class AuthPayload extends Payload {
	
	private long authCode = -1;
	
	/**
	 * The standard constructor for creating an AuthPayload
	 * @param emailId The emailId of the user to be authenticated
	 * @param passwd The password of the user to be authenticated
	 */
	public AuthPayload( long authCode ) {
		this.authCode = authCode;
	}

	public long getAuthCode() {
		return authCode;
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
		
		int authCodeHash = authCode == -1 ? 43 : new Long( authCode ).hashCode();
		
		return authCodeHash;
	}

}
