package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.RegisterPayload;

public class RegisterBean extends Bean {
	
	public RegisterBean( int clazz, char section, int rollNo, String emailId, 
			  			 String passwd ) {
		super( Header.REGISTER, new RegisterPayload( clazz, section, rollNo, emailId, passwd ) );
	}

}
