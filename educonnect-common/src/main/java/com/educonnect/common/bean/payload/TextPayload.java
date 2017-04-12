package com.educonnect.common.bean.payload;

/**
 * A TextPayload is a concrete implementation of a {@link Payload}. It contains 
 * all the required fields required for sending a text.  
 * @author Sensei
 *
 */
public class TextPayload extends Payload {
	
	private String sender = null;
	private String text   = null;
	
	/**
	 * The standard constructor for creating a TextPayload
	 * 
	 * @param sender The sender of the text
	 * @param text   The content of the text
	 */
	public TextPayload( String sender, String text ) {
		this.sender = sender;
		this.text   = text;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public boolean equals( Object obj ) {

		if( obj instanceof TextPayload && obj.hashCode() == this.hashCode() ) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {

		int senderHash = sender == null ? 17 : sender.hashCode();
		int textHash   = text   == null ? 19 : text.hashCode();
		
		return senderHash + textHash;
	}
}
