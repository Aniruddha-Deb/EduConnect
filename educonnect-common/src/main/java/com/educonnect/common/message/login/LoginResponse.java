package com.educonnect.common.message.login;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.core.Response;

public class LoginResponse extends Response {

	private static final long serialVersionUID = 5221620821985769924L;
	
	private boolean loginResult = false ;
	
	public LoginResponse( ResponseStatus status, String requestUID, boolean loginResult ) {
		super( MessageType.MT_LOGIN_RES, status, requestUID ) ;
		this.loginResult = loginResult;
	}
	
	public void setLoginResult( boolean result ) {
		this.loginResult = result ;
	}
	
	public boolean getLoginResult() {
		return this.loginResult ;
	}
	
	public LoginResponse withStatusText( String statusText ) {
		super.setStatusText( statusText );
		return this;
	}
}
