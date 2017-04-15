package com.educonnect.common.parser;

import com.educonnect.common.bean.payload.AuthPayload;
import com.educonnect.common.bean.payload.FailPayload;
import com.educonnect.common.bean.payload.FilePayload;
import com.educonnect.common.bean.payload.InfoPayload;
import com.educonnect.common.bean.payload.LoginPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.RegisterPayload;
import com.educonnect.common.bean.payload.ShutdownPayload;
import com.educonnect.common.bean.payload.TextPayload;
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

	private static String[] parts = null;
	
	private static final String PAYLOAD = "PAYLOAD";
	private static final String HEADER  = "HEADER";
	
	/**
	 * This is the static method that handles the parsing of received beans and 
	 * returns the payload carried by the bean 
	 * 
	 * @param receivedString The string received by the connection
	 * @return A deserialized Payload object containing the payload carried by 
	 * the string 
	 */
	public static Payload parse( String receivedString ) {
		if( receivedString == null ) return null;
		
		int splitIndex = receivedString.indexOf( ";" ) ;
		parts = new String[2];
		parts[0] = receivedString.substring( 0, splitIndex );
		parts[1] = receivedString.substring( splitIndex+1 );
		
		String header = parseHeader( parts[0] );
		Payload payload = parsePayload( parts[1], header );
		
		return payload;
	}
	
	private static String parseHeader( String receivedString ) {
		String[] headerSubParts = receivedString.split( "=" );
		
		if( !headerSubParts[0].equals( HEADER ) ) {
			throw new IllegalArgumentException( "Header flag missing" );
		}
		
		String header;
		try {
			header = headerSubParts[1];
		} catch( ArrayIndexOutOfBoundsException e ) {
			throw new IllegalArgumentException( "Bad bean passed to parser" );
		}
		
		return header.trim();
	}

	private static Payload parsePayload( String receivedString, String header ) {
		receivedString.trim();
		String[] payloadSubParts = receivedString.split( "=" );
		payloadSubParts[0] = payloadSubParts[0].trim();
		
		if( !payloadSubParts[0].equals( PAYLOAD ) ) {
			throw new IllegalArgumentException( "Payload flag missing" );			
		}
		
		String payload;
		try {
			payload = payloadSubParts[1];
		} catch( ArrayIndexOutOfBoundsException e ) {
			throw new IllegalArgumentException( "Bad payload in bean passed to parser" );
		}
		Gson gson = new Gson();
		Class<?> payloadInstance = null;
		
		switch( header ) {
			
			case "LOGIN" : payloadInstance = LoginPayload.class;
			break;
			
			case "TEXT"  : payloadInstance = TextPayload.class;
			break;
			
			case "FILE"  : payloadInstance = FilePayload.class;
			break;
			
			case "AUTH"  : payloadInstance = AuthPayload.class;
			break;
			
			case "FAIL"  : payloadInstance = FailPayload.class;
			break;
			
			case "INFO"  : payloadInstance = InfoPayload.class;
			break;
			
			case "SHUTDOWN"  : payloadInstance = ShutdownPayload.class;
			break;
			
			case "REGISTER" : payloadInstance = RegisterPayload.class;
			break;
			
			default: throw new IllegalArgumentException( "Bad header passed to parsePayload" );
		}
		
		return gson.fromJson( payload, payloadInstance );
	}

}
