package com.educonnect.common.bean.payload;

public class FailPayload extends Payload {

	String cause = null;
	
	public FailPayload( String cause ) {
		this.cause = cause;
	}
	
	public String getCause() {
		return cause;
	}
}
