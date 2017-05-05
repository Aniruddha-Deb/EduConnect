package com.educonnect.server.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.core.Message;
import com.educonnect.common.message.core.Request;
import com.educonnect.common.message.shutdown.ShutdownResponse;
import com.educonnect.common.serializer.Serializer;
import com.educonnect.server.payload.PayloadHandler;

public abstract class Client {
	
	private static final Logger log = Logger.getLogger( Client.class );
	
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
		} catch ( IOException e ) {
			log.error( "Could not initialize BufferedWriter", e );
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
		log.debug( "Sending message to client " + clientName );
		try {
			writer.write( stringToSend );
			writer.flush();
		} catch ( IOException e ) {
			log.error( "Could not send message across the network", e );
		}
	}
	
	public void receive( Request r ) {
		log.debug( "Received request from client " + clientName );
		PayloadHandler.handleResponse( r, this );
	}
	
	public void shutdown( String UID ) {
		ClientHandler.remove( this );
		try {
			writer.write( Serializer.serialize( new ShutdownResponse( UID ) ) );
			writer.flush();
		} catch( IOException e ) {
			log.error( "Could not send ShutdownResponse across the network", e );
		}
	}	
}
