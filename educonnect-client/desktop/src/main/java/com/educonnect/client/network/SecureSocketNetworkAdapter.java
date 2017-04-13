package com.educonnect.client.network;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.educonnect.client.network.receiver.SecureSocketReceiver;
import com.educonnect.client.network.sender.SecureSocketSender;
import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.ShutdownBean;
import com.educonnect.common.bean.payload.Payload;

public class SecureSocketNetworkAdapter implements NetworkAdapter {

	private static final String TRUSTSTORE_PASSWD = "public";
	private static final String TRUSTSTORE_LOC    = "src/main/resources/client.truststore";	
	
	private BlockingQueue<Bean>    sentBeans       = null;
	private BlockingQueue<Payload> receivedPayload = null;
	
	private SSLSocket sslSocket = null;
	
	private SecureSocketSender sender     = null;
	private SecureSocketReceiver receiver = null;
	
	public SecureSocketNetworkAdapter( String ipAddress, int port ) {
		sslSocket = setUpSSLSocket( ipAddress, port );
		
		sentBeans = new LinkedBlockingQueue<>();
		receivedPayload = new LinkedBlockingQueue<>();
		
		sender = new SecureSocketSender( sslSocket, sentBeans );
		receiver = new SecureSocketReceiver( sslSocket, this );
		
		new Thread( sender, "Sender" ).start();
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
			sentBeans.put( b );
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void receive( Payload p ) {
		try {
			receivedPayload.put( p );
			// just for now
			DummyClient.updateUI( p );
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
			sentBeans.put( new ShutdownBean() );
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
	}
	
}
