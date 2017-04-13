package com.educonnect.common.bean.payload;

public class InfoPayload extends Payload {

	private String info = null;
	
	public InfoPayload( String info ) {
		this.info = info;
	}
	
	public String getInfo() {
		return info;
	}
}
