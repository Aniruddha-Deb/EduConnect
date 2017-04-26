package com.educonnect.common.message.payload;

/**
 * A ShudownPayload is a concrete implementation of a {@link Payload}. It informs 
 * the receiver that the other end is shutting down and helps in safely closing 
 * the socket and the UI.   
 * @author Sensei
 *
 */
public class ShutdownPayload extends Payload {
	
	private String content = null;
	
	/**
	 * The standard constructor for creating a ShutdownPayload
	 */
	public ShutdownPayload() {
		content = "Shut down request";
	}
	
	public String getContent() {
		return content;
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
		// some large arbitrary number. Note that two ShutdownPayloads will always 
		// be equal.
		return 21*31;
	}
}
