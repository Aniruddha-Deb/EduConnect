package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.AuthPayload;

/**
 * An AuthBean is a more concrete implementation of a {@link Bean} which is 
 * used only for sending Authentication info across the connection. By default, it 
 * sets the header to {@link Header#AUTH} and the payload to an {@link AuthPayload}, 
 * which is created based on the parameters passed to the constructor.  
 * 
 * @author Sensei
 */
public class AuthBean extends Bean {
	
	/**
	 * The standard constructor for creating an AuthBean
	 * 
	 * @param emailId The email ID of the person getting authenticated
	 * @param passwd  The password of the person getting authenticated
	 */
	public AuthBean( String emailId, String passwd ) {
		super( Header.AUTH, new AuthPayload( emailId, passwd ) );
	}
}
