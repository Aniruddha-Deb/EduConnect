package com.educonnect.client.network.dummyserver;

import java.util.ArrayList;
import java.util.List;

import com.educonnect.common.bean.InfoBean;

public class DummyServerClientHandler {
	
	private static List<DummyClientModel> clients = new ArrayList<>();
	
	public static void handleClient( DummyClientModel client ) {
		clients.add( client );
	}
	
	public static void broadcast( String s, DummyClientModel sender ) {
		System.out.println( "Sending " + s + " On request of " + sender.getName() );
		for( DummyClientModel c : clients ) {
			if( !c.equals( sender ) ) {
				c.send( s );
			}
		}
		sender.send( new InfoBean( "Sent to all" ) );
	}

}
