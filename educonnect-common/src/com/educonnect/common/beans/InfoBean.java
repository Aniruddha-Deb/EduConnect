package com.educonnect.common.beans;

public class InfoBean extends Bean {
	
	public InfoBean( String payload ) {
		super.header = BeanConstants.INFO;
		super.payload = payload;
	}

}
