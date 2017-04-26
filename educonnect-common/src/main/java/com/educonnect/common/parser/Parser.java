package com.educonnect.common.parser;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Message;
import com.educonnect.common.message.login.LoginRequest;
import com.educonnect.common.message.login.LoginResponse;
import com.educonnect.common.message.payload.Payload;
import com.educonnect.common.message.shutdown.ShutdownRequest;
import com.educonnect.common.message.shutdown.ShutdownResponse;
import com.google.gson.Gson;

/**
 * This is the class which handles the parsing of the String coming over the 
 * socket, which contains a header and a payload. This class extracts the paylaod 
 * based on the header of the bean and returns it as a generic {@link Payload} 
 * object.</p> 
 * 
 * The parse method is a static method and does not require a new Parser object 
 * to be created. This is because the template for all beans of the EduConnect 
 * protocol is the same.
 * 
 * @author Sensei
 *
 */
public class Parser {

	private static final Gson GSON = new Gson();
	
	/**
	 * This is the static method that handles the parsing of received beans and 
	 * returns the payload carried by the bean 
	 * 
	 * @param receivedString The string received by the connection
	 * @return A deserialized Payload object containing the payload carried by 
	 * the string 
	 */
	public static Message parse( String header, String payload ) {
		if( header == null ) return null;
		
		MessageType msgType = GSON.fromJson( header, MessageType.class );
		Message m = parseMessage( msgType, payload ); 
		
		return m;
	}
	
	private static Message parseMessage( MessageType msgType, String payload ) {
		
		switch( msgType ) {
			case MT_LOGIN_RES:
				return GSON.fromJson( payload, LoginResponse.class );
				
			case MT_LOGIN_REQ:
				return GSON.fromJson( payload, LoginRequest.class );
				
			case MT_SHUTDOWN_REQ:
				return GSON.fromJson( payload, ShutdownRequest.class );				
				
			case MT_SHUTDOWN_RES:
				return GSON.fromJson( payload, ShutdownResponse.class );
		}
		return null;
	}
}
