package com.educonnect.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.ShutdownPayload;
import com.educonnect.common.parser.Parser;

public class ClientSocketReader implements Runnable{

	private Client client = null;
	private Socket socket = null; 
	private BufferedReader reader = null;
	
	public ClientSocketReader( Socket s, Client client ) {
		this.client = client;
		this.socket = s;
		
		try {
			this.reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		Payload p;
		try {		
			String s = reader.readLine();
			p = Parser.parse( s );
			
			while( !( p instanceof ShutdownPayload ) ) {
				client.receive( p );
				s = reader.readLine();
				
				if( s != null ) {
					p = Parser.parse( s );
				}
			}
			System.out.println( "Client " + client.getClientName() + " is shutting down" );
			client.shutdown();
			socket.close();
			reader.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
