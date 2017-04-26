package com.educonnect.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.educonnect.common.message.core.Request;
import com.educonnect.common.message.shutdown.ShutdownRequest;
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
		
		Request r = null;
		try {		
			r = (Request)Parser.parse( ClientHandler.readHeader(), ClientHandler.readPayload() );
			
			while( !( r instanceof ShutdownRequest ) ) {
				client.receive( r );
				r = (Request)Parser.parse( ClientHandler.readHeader(), ClientHandler.readPayload() );
			}
			client.shutdown( r.getUID() );
			socket.close();
			reader.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
