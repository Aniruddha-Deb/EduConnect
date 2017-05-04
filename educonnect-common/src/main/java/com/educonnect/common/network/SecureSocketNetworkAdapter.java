package com.educonnect.common.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Hashtable;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

import com.educonnect.common.engine.Engine;
import com.educonnect.common.message.core.Request;
import com.educonnect.common.message.core.Response;
import com.educonnect.common.message.shutdown.ShutdownRequest;
import com.educonnect.common.network.receiver.SecureSocketReceiver;
import com.educonnect.common.network.response.ResponseContainer;
import com.educonnect.common.serializer.Serializer;

public class SecureSocketNetworkAdapter implements NetworkAdapter {

	private Hashtable<String, ResponseContainer> responses = null;
	
	private SSLSocket sslSocket = null;
	private String ipAddress = null;
	private int    port      = -1;

	private BufferedWriter writer = null;
	
	private SecureSocketReceiver receiver = null;
	private Thread receiverThread = null;
	
	private Engine engine = null;
	
	public SecureSocketNetworkAdapter( String ipAddress, int port, Engine engine ) {
		this.engine = engine;
		this.ipAddress = ipAddress;
		this.port = port;
		
		responses = new Hashtable<>();
	}
	
	private SSLSocket setUpSSLSocket( String ipAddress, int port ) {
		System.setProperty( "javax.net.ssl.trustStore", engine.getLocation() );
		System.setProperty( "javax.net.ssl.trustStorePassword", engine.getPasswd() );

		SSLSocketFactory factory = ( SSLSocketFactory )SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		try {
			socket = ( SSLSocket )factory.createSocket( ipAddress, port );
		} catch ( IOException e ) {
			JOptionPane.showMessageDialog( null, "Unable to connect to server.\n" + 
												 "Either your internet is down or \n" + 
												 "the server is unreachable." );
		}
		
		return socket;
	}
	
	public void connect() {
		this.sslSocket = setUpSSLSocket( ipAddress, port );
		if( sslSocket != null ) {
			try {
				writer = new BufferedWriter( new OutputStreamWriter( sslSocket.getOutputStream() ) );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			receiver = new SecureSocketReceiver( sslSocket, this );
			
			receiverThread = new Thread( receiver, "Receiver" );
			receiverThread.start();
		}
	}
	
	public void putResponse( Response r ) {
		engine.handleAsyncResponse( r );
		ResponseContainer resContatiner = responses.get( r.getCorrelationId() );
		if( resContatiner != null ) {
			resContatiner.setResponse( r );
		}
	}
	
	public Thread getReceiverThread() {
		return receiverThread;
	}
	
	public boolean isOpen() {
		return (sslSocket != null && !(sslSocket.isClosed()) );
	}
	
	@Override
	public void shutdown() {
		try {
			writer.write( Serializer.serialize( new ShutdownRequest() ) );
			writer.flush();
			
			while( receiverThread.isAlive() ) {
				try {
					Thread.sleep( 10 );
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
			}
			writer.close();
			sslSocket.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendAsync( Request r ) {
		try {
			String s = Serializer.serialize( r );
			writer.write( s );
			writer.flush();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	@Override
	public Response send( Request request ) {
		Response response = null;
		try {
			writer.write( Serializer.serialize( request ) );
			writer.flush();
			ResponseContainer r = new ResponseContainer();
			responses.put( request.getUID(), r );
			response = r.waitForResponse();
		} catch( Exception e ) {
			e.printStackTrace();
		}	
		return response;
	}
	
}
