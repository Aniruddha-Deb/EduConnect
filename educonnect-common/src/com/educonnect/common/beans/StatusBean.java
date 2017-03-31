package com.educonnect.common.beans;

public class StatusBean extends Bean {
	
	private StatusBean( BeanConstants header, String payload ) {
		super.header = header.toString();
		super.payload = payload;
	}
	
	public static StatusBean createSuccessBean( String payload ) {
		return new StatusBean( BeanConstants.SUCCESS, payload );
	}
	
	public static StatusBean createFailureBean( String payload ) {
		return new StatusBean( BeanConstants.FAILURE, payload );
	}

}
