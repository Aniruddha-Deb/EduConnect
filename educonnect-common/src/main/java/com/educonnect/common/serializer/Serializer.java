package com.educonnect.common.serializer;

import com.educonnect.common.message.core.Message;
import com.google.gson.Gson;

public class Serializer {
	
	private static Gson GSON = new Gson();

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
