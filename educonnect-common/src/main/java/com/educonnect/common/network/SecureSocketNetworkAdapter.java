package com.educonnect.common.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.ShutdownBean;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.engine.Engine;
import com.educonnect.common.network.receiver.SecureSocketReceiver;
import com.educonnect.common.serializer.Serializer;

public class SecureSocketNetworkAdapter implements NetworkAdapter {

	private static final String TRUSTSTORE_PASSWD = "public";
	private static final String TRUSTSTORE_LOC    = "src/main/resources/client.truststore";	
	
	private BlockingQueue<Payload> receivedPayload = null;
	
	private SSLSocket sslSocket = null;

	private BufferedWriter writer = null;
	
	private SecureSocketReceiver receiver = null;
	
	private Engine engine = null;
	
	public SecureSocketNetworkAdapter( String ipAddress, int port, Engine engine ) {
		sslSocket = setUpSSLSocket( ipAddress, port );
		
		receivedPayload = new LinkedBlockingQueue<>();
		
		try {
			writer = new BufferedWriter( new OutputStreamWriter( sslSocket.getOutputStream() ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		receiver = new SecureSocketReceiver( sslSocket, this );
		
		new Thread( receiver, "Receiver" ).start();
	}
	
	private SSLSocket setUpSSLSocket( String ipAddress, int port ) {
		System.setProperty( "javax.net.ssl.trustStore", TRUSTSTORE_LOC );
		System.setProperty( "javax.net.ssl.trustStorePassword", TRUSTSTORE_PASSWD );

		SSLSocketFactory factory = ( SSLSocketFactory )SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		try {
			socket = ( SSLSocket )factory.createSocket( ipAddress, port );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		return socket;
	}

	@Override
	public void send( Bean b ) {
		try {
			String s = Serializer.serialize( b );
			writer.write( s );
			writer.flush();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void receive( Payload p ) {
		try {
			receivedPayload.put( p );
			engine.handle( p );
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}
	}
	
	public Payload get() {
		try {
			return receivedPayload.take();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void shutdown() {
		try {
			writer.write( Serializer.serialize( new ShutdownBean() ) );
			writer.flush();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
}
