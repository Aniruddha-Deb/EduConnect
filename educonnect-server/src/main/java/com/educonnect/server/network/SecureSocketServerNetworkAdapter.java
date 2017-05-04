package com.educonnect.server.network;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.educonnect.server.client.ClientHandler;

public class SecureSocketServerNetworkAdapter implements ServerNetworkAdapter {
	
	SSLServerSocket serverSocket = null;
	
	public SecureSocketServerNetworkAdapter( String keyStoreLocation, 
											 String password, 
											 int port ) {
		System.setProperty( "javax.net.ssl.keyStore", keyStoreLocation );
		System.setProperty( "javax.net.ssl.keyStorePassword", password );

		SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		try {
			serverSocket = (SSLServerSocket)factory.createServerSocket( port );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void receiveClients() {
		try {
			while( true ) {
				SSLSocket sslSocket = (SSLSocket)serverSocket.accept();
				ClientHandler.handle( sslSocket );
			}
		} catch( IOException ex ) {
			ex.printStackTrace();
		}
	}

	@Override
	public void shutdown() {
		try {
			if( serverSocket.isBound() ) {
				serverSocket.accept();
				serverSocket.close();
			}
			else {
				serverSocket.close();
			}
		} catch( IOException ex ) {
			ex.printStackTrace();
		}
	}
}
