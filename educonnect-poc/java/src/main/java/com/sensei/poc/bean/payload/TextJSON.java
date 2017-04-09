package com.sensei.poc.bean.payload;

public class TextJSON extends JSONPayload {

	private String text   = null;
	private String sender = null;
	
	public TextJSON( String sender, String text ) {
		this.sender = sender;
		this.text = text;		
	}
	
	public String getText() {
		return text;
	}
	
	public String getSender() {
		return sender;
	}
}
