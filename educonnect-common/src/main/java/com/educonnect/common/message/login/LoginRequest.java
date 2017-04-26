package com.educonnect.common.message.login;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Request;

public class LoginRequest extends Request {

	private static final long serialVersionUID = -3067878513768614728L;
	
	private String userId = null ;
	private String password = null ;

	public LoginRequest( String userId, String password ) {
		super( MessageType.MT_LOGIN_REQ ) ;
		this.userId = userId ;
		this.password = password ;
	}
	
	public String getUserId() {
		return this.userId ;
	}
	
	public String getPassword() {
		return this.password ;
	}
}
