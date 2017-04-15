package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.LoginPayload;
import com.educonnect.common.client.ClientType;

/**
 * A LoginBean is a more concrete implementation of a {@link Bean} which is 
 * used only for sending login requests across the connection. By default, it 
 * sets the header to {@link Header#LOGIN} and the payload to a {@link LoginPayload}, 
 * which is created based on the parameters passed to the constructor.  
 * 
 * @author Sensei
 */
public class LoginBean extends Bean {

	/**
	 * The standard constructor for creating a LoginBean
	 * 
	 * @param emailId    The eMail ID of the user logging in
	 * @param password   The password of the user logging in
	 * @param clientType The type of user logging in - Can either be an ADMIN or 
	 * a STUDENT
	 */
	public LoginBean( String emailId, String password, ClientType clientType ) {
		super( Header.LOGIN, new LoginPayload( emailId, password, clientType ) );
	}	
}
