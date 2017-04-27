package com.educonnect.common.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.educonnect.common.engine.Engine;
import com.educonnect.common.message.core.Request;
import com.educonnect.common.message.core.Response;
import com.educonnect.common.message.shutdown.ShutdownRequest;
import com.educonnect.common.network.receiver.SecureSocketReceiver;
import com.educonnect.common.serializer.Serializer;

/**
 * A SecureSocketNetworkAdapter is an implementation of NetworkAdapter which 
 * uses TLS or SSL secured sockets for communication. It contains both a sender 
 * and a receiver and encapsulates all the connection IO from the program.
 * 
 * @author Sensei
 *
 */
public class SecureSocketNetworkAdapter implements NetworkAdapter {

	private BlockingQueue<Response> responses = null;
	
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
		
		responses = new LinkedBlockingQueue<>();
		
	}
	
	private SSLSocket setUpSSLSocket( String ipAddress, int port ) {
		System.setProperty( "javax.net.ssl.trustStore", engine.getLocation() );
		System.setProperty( "javax.net.ssl.trustStorePassword", engine.getPasswd() );

		SSLSocketFactory factory = ( SSLSocketFactory )SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		try {
			socket = ( SSLSocket )factory.createSocket( ipAddress, port );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		return socket;
	}
	
	public void connect() {
		this.sslSocket = setUpSSLSocket( ipAddress, port );
		try {
			writer = new BufferedWriter( new OutputStreamWriter( sslSocket.getOutputStream() ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		receiver = new SecureSocketReceiver( sslSocket, this );
		
		receiverThread = new Thread( receiver, "Receiver" );
		receiverThread.start();
	}
	
	public Thread getReceiverThread() {
		return receiverThread;
	}
	
	public boolean isOpen() {
		return (sslSocket != null && !(sslSocket.isClosed()) );
	}
	
	public boolean isClosed() {
		return (sslSocket.isClosed());
	}

	public BlockingQueue<Response> getResponses() {
		return responses;
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
			System.out.println( "Closed receiverThread" );
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
	public Response send( Request r ) {
		Response response = null;
		
		try {
			String s = Serializer.serialize( r );
			writer.write( s );
			writer.flush();
			response = responses.take();
		} catch( Exception e ) {
			e.printStackTrace();
		}		
		return response;
	}
	
}
