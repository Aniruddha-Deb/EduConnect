package com.educonnect.server.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.core.Message;
import com.educonnect.common.message.core.Request;
import com.educonnect.common.message.shutdown.ShutdownResponse;
import com.educonnect.common.serializer.Serializer;
import com.educonnect.server.payload.PayloadHandler;

public abstract class Client {
	
	private ClientSocketReader reader = null;
	private BufferedWriter writer = null; 
	
	private Thread receiverThread = null;
	
	protected int UID = -1;
	protected String clientName = null;
	protected ClientType clientType = null;
	
	public Client( Socket socket, int UID, ClientType clientType ) {
		
		this.UID = UID;
		this.clientType = clientType;
		
		this.reader = new ClientSocketReader( socket, this );
		receiverThread = new Thread( reader, "Reader" );
		receiverThread.start();
		
		try {
			this.writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public ClientType getClientType() {
		return clientType;
	}
	
	public int getUID() {
		return UID;
	}
	
	public void send( Message m ) {
		String stringToSend = Serializer.serialize( m );
		
		try {
			writer.write( stringToSend );
			writer.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	public void send( String s ) {
		try {
			writer.write( s + "\n" );
			writer.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public void receive( Request r ) {
		PayloadHandler.handleResponse( r, this );
	}
	
	public void shutdown( String UID ) {
		System.out.println( "Shutting down client" );
		ClientHandler.remove( this );
		try {
			writer.write( Serializer.serialize( new ShutdownResponse( UID ) ) );
			writer.flush();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}	
}
