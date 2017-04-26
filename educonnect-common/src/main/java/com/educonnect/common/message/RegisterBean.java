package com.educonnect.common.message;

import com.educonnect.common.message.header.Header;
import com.educonnect.common.message.payload.RegisterPayload;

/**
 * A RegisterBean is a more concrete implementation of a {@link Bean} which is 
 * used only for sending registration requests across the connection. By default, it 
 * sets the header to {@link Header#REGISTER} and the payload to a {@link RegisterPayload}, 
 * which is created based on the parameters passed to the constructor.  
 * 
 * @author Sensei
 */
public class RegisterBean extends Bean {
	
	/**
	 * The standard constructor for creating a registration bean
	 * 
	 * @param clazz   The class or grade to which the student belongs
	 * @param section The section to which the student belongs
	 * @param rollNo  The roll number or unique number assigned to the student 
	 * @param emailId The email ID with which the student registers himself
	 * @param passwd  The password of the student
	 */
	public RegisterBean( int clazz, char section, int rollNo, String emailId, 
			  			 String passwd ) {
		super( Header.REGISTER, new RegisterPayload( clazz, section, rollNo, emailId, passwd ) );
	}

}
