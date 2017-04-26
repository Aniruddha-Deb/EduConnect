package com.educonnect.common.message.payload;

/**
 * An InfoPayload is a concrete implementation of a {@link Payload}. It contains 
 * general information about the operation carried out by the client. It is used for
 * returning data that the client requested in his transaction with the server.  
 * @author Sensei
 *
 */
public class InfoPayload extends Payload {

	private String info = null;
	
	/**
	 * The standard constructor for creating an InfoPayload
	 * @param info The information to be given to the client.
	 */
	public InfoPayload( String info ) {
		this.info = info;
	}
	
	public String getInfo() {
		return info;
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
		
		int infoHash = info == null ? 67 : info.hashCode();
		
		return infoHash;
	}
}
