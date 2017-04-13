package com.educonnect.client.network.dummyserver;

import java.util.HashMap;
import java.util.Map;

public class DummyClientDB {
	
	private static Map<Integer, String> studentDB = new HashMap<>(); 
	
	static {
		studentDB.put( 11, "Aniruddha Deb" );
		studentDB.put( 14, "Arnav Gore" );
		studentDB.put( 30, "Varun Patil" );
		studentDB.put( 34, "Rasesh Ramadesikan" );		
		studentDB.put( 46, "Ranai Srivastav" );		
	}

	public static String getName( int rollNo ) {
		if( studentDB.containsKey( new Integer( rollNo ) ) ) {
			return studentDB.get( new Integer( rollNo ) );
		}
		return null;
	}
}
