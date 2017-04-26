package com.educonnect.common.serializer;

import com.educonnect.common.message.core.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * This class handles the serializing of Java object beans into strings which 
 * are then sent over the network. It follows the EduConnect protocol while 
 * serializing the beans.</p>
 * 
 * The serializer serializes the Header flag of the bean as a generic string whereas 
 * the payload is serialized as a JSON string.
 * 
 * @author Sensei
 *
 */
public class Serializer {
	
	private static Gson GSON = new Gson();

	/**
	 * The static method which handles the serialization of a bean to a string which 
	 * can readily be sent over the connection. 
	 * 
	 * @param bean The bean object to be serialized
	 * @return A serialized bean in the form of a String
	 */
	public static String serialize( Message msg ) {
		StringBuilder msgStr = new StringBuilder();
		
		String msgHeader  = getMessageHeaderAsJSON( msg );
		String msgPayload = getMessagePayloadAsJSON( msg );
		
		msgStr.append( msgHeader.length() ).append( "\n" ) ;
		msgStr.append( msgHeader ).append( "\n" ) ;
		msgStr.append( msgPayload.length() ).append( "\n" ) ;
		msgStr.append( msgPayload ) .append( "\n" ) ;
		
		return msgStr.toString();
	}
	
	private static String getMessageHeaderAsJSON( Message m ) {
		return GSON.toJson( m.getMessageType() ) ;
	}
	
	private static String getMessagePayloadAsJSON( Message m ) {
		return GSON.toJson( m ) ;
	}
}
