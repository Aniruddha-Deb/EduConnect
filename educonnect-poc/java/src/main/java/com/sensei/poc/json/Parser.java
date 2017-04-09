package com.sensei.poc.json;

import com.google.gson.Gson;
import com.sensei.poc.bean.Bean;
import com.sensei.poc.bean.header.BeanHeader;
import com.sensei.poc.bean.payload.JSONPayload;
import com.sensei.poc.bean.payload.LoginJSON;
import com.sensei.poc.bean.payload.TextJSON;

public class Parser {
	
	public static JSONPayload parse( String receivedString ) {
		BeanHeader header = parseBeanHeader( receivedString );
		JSONPayload payload = parseJSONPayload( receivedString, header );
		
		return payload;
	}
	
	private static BeanHeader parseBeanHeader( String receivedString ) {
		String[] parts = receivedString.split( "\n" );
		String[] headerSubPart = parts[0].split( "=" );
		
		String header;
		try {
			header = headerSubPart[1];
		} catch( ArrayIndexOutOfBoundsException e ) {
			throw new IllegalArgumentException( "Bad bean passed to parser" );
		}
		
		switch ( header ) {
			
			case "TEXT"  : return BeanHeader.TEXT;
			case "LOGIN" : return BeanHeader.LOGIN;

			default: throw new IllegalArgumentException( "Undefined bean header used" );
		}
	}

	private static JSONPayload parseJSONPayload( String receivedString, BeanHeader header ) {
		int startOfJSON = receivedString.indexOf( "{" );
		String jsonString = receivedString.substring( startOfJSON );
		
		Gson gson = new Gson();
		
		switch( header ) {
		
			case LOGIN : return gson.fromJson( jsonString, LoginJSON.class );
			case TEXT  : return gson.fromJson( jsonString, TextJSON.class );
			
			default: return gson.fromJson( jsonString, Bean.class );
		}
	}
}
