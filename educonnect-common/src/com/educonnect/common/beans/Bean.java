package com.educonnect.common.beans;

public abstract class Bean {
	
	public enum BeanConstants {
		OK, TEXT, SUCCESS, FAILURE, LOGIN;
	}
	
	protected String header = null;
	protected String payload = null;
	
	public String getHeader() {
		return header;
	}
	
	public String getData() {
		return payload;
	}
	
}
