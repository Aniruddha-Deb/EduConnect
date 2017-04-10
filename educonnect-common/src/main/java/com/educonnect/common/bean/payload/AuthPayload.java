package com.educonnect.common.bean.payload;

public class AuthPayload extends Payload {
	
	private String emailId = null;
	private String passwd  = null;
	
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
