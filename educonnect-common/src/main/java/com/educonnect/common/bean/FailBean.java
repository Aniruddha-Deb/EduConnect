package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.FailPayload;

/**
 * A FailBean is a more concrete implementation of a {@link Bean} which is 
 * used only for sending failure information across the connection. By default, it 
 * sets the header to {@link Header#FAIL} and the payload to a {@link FailPayload}, 
 * which is created based on the parameters passed to the constructor.  
 * 
 * @author Sensei
 */
public class FailBean extends Bean {
	
	/**
	 * The standard constructor for creating a FailBean
	 * 
	 * @param cause The cause for failure of the request.
	 */
	public FailBean( String cause ) {
		super( Header.FAIL, new FailPayload( cause ) );
	}
}
