package com.educonnect.common.bean.payload;

/**
 * A FailPayload is a concrete implementation of a {@link Payload}. It tells the 
 * client that his operation failed and contains a cause which states why it failed.  
 * @author Sensei
 *
 */
public class FailPayload extends Payload {

	String cause = null;
	
	/**
	 * The standard constructor for creating a FailPayload
	 * @param cause The reason for failure of the operation of the client
	 */
	public FailPayload( String cause ) {
		this.cause = cause;
	}
	
	public String getCause() {
		return cause;
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
		int causeHash = cause == null ? 61 : cause.hashCode();
		
		return causeHash;
	}
}
