package com.educonnect.common.beans;

import java.io.Serializable;

public abstract class Bean implements Serializable{
	
	private static final long serialVersionUID = 8864022887689600887L;

	public enum BeanConstants {
		OK, INFO, TEXT, SUCCESS, FAILURE, LOGIN, IMAGE;
	}
	
	protected BeanConstants header = null;
	protected String payload = null;
	
	public BeanConstants getHeader() {
		return header;
	}
	
	public String getData() {
		return payload;
	}
	
}
