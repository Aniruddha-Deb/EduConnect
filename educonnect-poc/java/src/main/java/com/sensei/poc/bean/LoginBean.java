package com.sensei.poc.bean;

import com.sensei.poc.bean.header.BeanHeader;
import com.sensei.poc.bean.payload.LoginJSON;

public class LoginBean extends Bean {
	
	public LoginBean( LoginJSON payload ) {
		super( BeanHeader.LOGIN, payload );
	}
}
