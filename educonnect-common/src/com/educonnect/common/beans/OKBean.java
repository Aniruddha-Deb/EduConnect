package com.educonnect.common.beans;

public class OKBean extends Bean {
	
	public OKBean( String payload ) {
		super.header = BeanConstants.OK.toString();
		super.payload = payload;
	}

}
