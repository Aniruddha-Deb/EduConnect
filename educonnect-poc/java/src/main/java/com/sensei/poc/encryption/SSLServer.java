package com.sensei.poc.encryption;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SSLServer {

	
	public static final int    SERVER_PORT_NO    = 1132;
	public static final String KEYSTORE_PASSWD = "EduConnect";
	public static final String KEYSTORE_LOC    = "src/main/resources/keystore.server";

	public static void main( String[] args ) throws Exception {

		System.setProperty( "javax.net.ssl.keyStore", KEYSTORE_LOC );
		System.setProperty( "javax.net.ssl.keyStorePassword", KEYSTORE_PASSWD );
		System.setProperty( "javax.net.debug" , "ssl" );
		
		SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		SSLServerSocket serverSocket = (SSLServerSocket)factory.createServerSocket( SERVER_PORT_NO );
		SSLSocket socket = (SSLSocket)serverSocket.accept();
		
		BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		String s = null;
		while( (s=br.readLine()) != null ) {
			System.out.println( s );
		}
	}
}
