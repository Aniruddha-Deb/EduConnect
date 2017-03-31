package com.educonnect.common.beans;

public class LoginBean extends Bean{
	
	public int rollNo = -1;
	public String passwd = null;
	
	public LoginBean( int rollNo, String passwd, String payload ) {
		super.header = BeanConstants.LOGIN;
		super.payload = payload;
		this.rollNo = rollNo;
		this.passwd = passwd;
	}
	
	public int getRollNo() {
		return rollNo;
	}
	
	public String getPasswd() {
		return passwd;
	}

}
