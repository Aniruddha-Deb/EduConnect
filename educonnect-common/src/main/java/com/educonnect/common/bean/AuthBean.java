package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.AuthPayload;

public class AuthBean extends Bean {
	
	public AuthBean( String emailId, String passwd ) {
		super( Header.AUTH, new AuthPayload( emailId, passwd ) );
	}
}
