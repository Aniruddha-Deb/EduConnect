package com.educonnect.common.bean.payload;

public class TextPayload extends Payload {
	
	private String sender = null;
	private String text   = null;
	
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
}
