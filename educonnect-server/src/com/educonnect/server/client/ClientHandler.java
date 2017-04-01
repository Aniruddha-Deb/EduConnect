package com.educonnect.server.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.educonnect.common.adapter.SocketNetworkAdapter;
import com.educonnect.common.beans.Bean;
import com.educonnect.common.beans.BeanParser;
import com.educonnect.common.beans.Bean.BeanConstants;

public class ClientHandler {
	
	private static List<Client> clients = new ArrayList<>();
	
	public static void handle( Socket clientSocket ) throws IOException {
		
		Bean b = null;
		SocketNetworkAdapter adapter = new SocketNetworkAdapter( clientSocket );
		
		try {
			b = adapter.receive();
			System.out.println( "B not null" );
			Client c = new Client( BeanParser.getRollNo( b ), adapter );
			c.start();
			clients.add( c );
			System.out.println( "Started a client thread" );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	public static void broadcast( Bean bean, Client sender ) throws Exception{
		for( Client c : clients ) {
			if( !c.equals( sender ) ) {
				c.getAdapter().send( bean );
			}
		}
		sender.getAdapter().send( new Bean( BeanConstants.INFO, "Sent" ) );
	}
	
}
