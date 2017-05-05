package com.educonnect.server.network;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.apache.log4j.Logger;

import com.educonnect.server.client.ClientHandler;

public class SecureSocketServerNetworkAdapter implements ServerNetworkAdapter {
	
	private static final Logger log = Logger.getLogger( 
								SecureSocketServerNetworkAdapter.class );
	
	private SSLServerSocket serverSocket = null;
	
	public SecureSocketServerNetworkAdapter( String keyStoreLocation, 
											 String password, 
											 int port ) {
		System.setProperty( "javax.net.ssl.keyStore", keyStoreLocation );
		System.setProperty( "javax.net.ssl.keyStorePassword", password );
		log.info( "Loaded keystore into property javax.net.ssl.keyStore" );
		SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		try {
			serverSocket = (SSLServerSocket)factory.createServerSocket( port );
		} catch ( IOException e ) {
			log.error( "Unable to create serverSocket", e );
		}
		log.info( "Created serverSocket" );
	}

	@Override
	public void receiveClients() {
		try {
			log.info( "Waiting for clients to connect." );
			while( true ) {
				SSLSocket sslSocket = (SSLSocket)serverSocket.accept();
				log.info( "Accepted a client" );
				ClientHandler.handle( sslSocket );
			}
		} catch( IOException ex ) {
			log.error( "Unable to receive client", ex );
		}
	}

	@Override
	public void shutdown() {
		try {
			log.info( "Shutting down serverSocket" );
			if( serverSocket.isBound() ) {
				serverSocket.accept();
				serverSocket.close();
			}
			else {
				serverSocket.close();
			}
		} catch( IOException ex ) {
			log.error( "Error while closing serverSocket", ex );
		}
	}
}
