package com.educonnect.common.bean.header;

import com.educonnect.common.bean.*;
import com.educonnect.common.bean.payload.*;
import com.educonnect.common.parser.Parser;

/**
 * A Header serves to indicate what kind of {@link Payload} the {@link Bean} is 
 * carrying. It is the first piece of information on a bean. By itself, it does 
 * not have much importance. It is only used by the {@link Parser} to contextually parse 
 * the Payload.   
 * 
 * @author Sensei
 * 
 */
public enum Header {
	
	/**
	 * Used to indicate a payload containing a Login request
	 * @see LoginBean
	 * @see LoginPayload
	 */
	LOGIN, 
	
	/**
	 * Used to indicate a payload containing Authentication information
	 * @see AuthBean
	 * @see AuthPayload
	 */
	AUTH, 

	/**
	 * Used to indicate a payload containing a text message
	 * @see TextBean
	 * @see TextPayload
	 */
	TEXT,
	
	/**
	 * Used to indicate a payload containing a file
	 * @see FileBean
	 * @see FilePayload
	 */
	FILE,
	
	/**
	 * Used to indicate a payload containing a shutdown request
	 * @see ShutdownBean
	 * @see ShutdownPayload
	 */
	SHUTDOWN,	
}
