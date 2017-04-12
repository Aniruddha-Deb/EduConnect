package com.educonnect.common.bean.payload;

public class ShutdownPayload extends Payload {
	
	private String content = null;
	
	public ShutdownPayload() {
		content = "Shut down request";
	}
	
	public String getContent() {
		return content;
	}
}
