package com.educonnect.common.message.login;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Request;

public class LoginRequest extends Request {

	private static final long serialVersionUID = -3067878513768614728L;
	
	private String userId = null ;
	private char[] password = null ;
	private ClientType clientType = null;

	public LoginRequest( String userId, char[] password, ClientType c ) {
		super( MessageType.MT_LOGIN_REQ ) ;
		this.userId = userId ;
		this.password = password ;
		this.clientType = c;
	}
	
	public String getUserId() {
		return this.userId ;
	}
	
	public char[] getPassword() {
		return this.password ;
	}
	
	public ClientType getClientType() {
		return clientType;
	}
}
