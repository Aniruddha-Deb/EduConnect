package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.InfoPayload;

/**
 * An InfoBean is a more concrete implementation of a {@link Bean} which is 
 * used only for sending general information across the connection. By default, it 
 * sets the header to {@link Header#INFO} and the payload to an {@link InfoPayload}, 
 * which is created based on the parameters passed to the constructor.  
 * 
 * @author Sensei
 */
public class InfoBean extends Bean {

	/**
	 * The standard constructor for creating an InfoBean
	 * @param info The information to be sent across the connection
	 */
	public InfoBean( String info ) {
		super( Header.INFO, new InfoPayload( info ) );
	}
}
