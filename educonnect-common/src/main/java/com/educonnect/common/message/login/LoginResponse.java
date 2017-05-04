package com.educonnect.common.message.login;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Response;

public class LoginResponse extends Response {

	private static final long serialVersionUID = 5221620821985769924L;
	
	private boolean loginResult = false ;
	private String loginName = null;
	
	public LoginResponse( String requestUID, boolean loginResult ) {
		super( MessageType.MT_LOGIN_RES, requestUID ) ;
		this.loginResult = loginResult;
	}
	
	public void setLoginResult( boolean result ) {
		this.loginResult = result ;
	}
	
	public boolean getLoginResult() {
		return this.loginResult ;
	}
	
	public String getLoginName() {
		return loginName;
	}
	
	public LoginResponse withStatusText( String statusText ) {
		super.setStatusText( statusText );
		return this;
	}
	
	public LoginResponse withLoginName( String loginName ) {
		this.loginName = loginName;
		return this;
	}
}
