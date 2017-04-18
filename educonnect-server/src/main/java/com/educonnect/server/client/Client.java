package com.educonnect.server.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.CommunicationConstants;
import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.bean.ShutdownBean;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.client.ClientType;
import com.educonnect.common.serializer.Serializer;
import com.educonnect.server.db.JDBCAdapter;
import com.educonnect.server.payload.PayloadHandler;

public class Client {
	
	private ClientSocketReader reader = null;
	private BufferedWriter writer = null; 
	
	private Thread receiverThread = null;
	
	private int UID = -1;
	private String clientName = null;
	private ClientType clientType = null;
	
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
		
		if( clientType.equals( ClientType.ADMIN ) ) {			
			this.clientName = JDBCAdapter.getInstance().getAdminName( UID );
			send( new InfoBean( CommunicationConstants.NAME_INFO + clientName ) );
			send( new InfoBean( CommunicationConstants.DB_HEADER_INFO + 
								JDBCAdapter.getInstance().getEditableClasses() ) );
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
	
	public void send( Bean b ) {
		String stringToSend = Serializer.serialize( b );
		
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

	public void receive( Payload p ) {
		PayloadHandler.handlePayload( p, this );
	}
	
	public void shutdown() {
		ClientHandler.remove( this );
		try {
			writer.write( Serializer.serialize( new ShutdownBean() ) );
			writer.flush();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void forceShutdown() {
		try {
			writer.write( Serializer.serialize( new ShutdownBean() ) );
			writer.flush();
			receiverThread.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
