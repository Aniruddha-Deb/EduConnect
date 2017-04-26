package com.educonnect.common.message;

import com.educonnect.common.message.header.Header;
import com.educonnect.common.message.payload.ShutdownPayload;

/**
 * A ShutdownBean is a more concrete implementation of a {@link Bean} which is 
 * used only for sending shutdown commands across the connection. By default, it 
 * sets the header to {@link Header#SHUTDOWN} and the payload to a {@link ShutdownPayload}, 
 * which is created based on the parameters passed to the constructor.  
 * 
 * @author Sensei
 */
public class ShutdownBean extends Bean {
	
	/**
	 * The standard constructor for creating a shutdown command. 
	 */
	public ShutdownBean() {
		super( Header.SHUTDOWN, new ShutdownPayload() );
	}

}
