package com.educonnect.common.beans;

public class BeanParser {
	
	// TODO make this more advanced and facilitate passwd parsing too
	public static int getRollNo( Bean b ) {
		String payload = (String)b.getPayload();
		String[] payloadParts = payload.split( "=" );
		return Integer.parseInt( payloadParts[1] );
	}
}
