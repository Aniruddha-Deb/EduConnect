package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.LoginPayload;

public class LoginBean extends Bean {

	public LoginBean( int grade, char section, int rollNo ) {
		super( Header.LOGIN, new LoginPayload( grade, section, rollNo ) );
	}
}
