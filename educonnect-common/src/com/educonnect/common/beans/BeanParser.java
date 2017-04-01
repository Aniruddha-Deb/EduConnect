package com.educonnect.common.beans;

/**
 * 
 * The BeanParser class is a parser which is meant to parse the Object payload 
 * contained in a bean. It parses the data according to the EduConnect protocol 
 * and returns the associated data based on the context of the header. 
 * 
 * note: Still in development and quite shabby at the moment. Please don't mind :)
 * 
 * @author Sensei
 *
 */
public class BeanParser {
	
	// TODO make this more advanced and facilitate passwd parsing too
	public static int getRollNo( Bean b ) {
		String payload = (String)b.getPayload();
		String[] payloadParts = payload.split( "=" );
		return Integer.parseInt( payloadParts[1] );
	}
}
