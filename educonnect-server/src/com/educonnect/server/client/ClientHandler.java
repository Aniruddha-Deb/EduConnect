package com.educonnect.server.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.educonnect.common.adapter.SocketNetworkAdapter;
import com.educonnect.common.beans.LoginBean;

public class ClientHandler {
	
	private static List<Client> clients = new ArrayList<>();
	
	
	public static void handle( Socket clientSocket ) throws IOException {
		
		LoginBean b = null;
		SocketNetworkAdapter adapter = new SocketNetworkAdapter( clientSocket );
		
		try {
			b = (LoginBean)adapter.receive();
			System.out.println( "B not null" );
			Client c = new Client( b.getRollNo(), adapter );
			c.start();
			//clients.add( c );
			System.out.println( "Started a client thread" );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
}
