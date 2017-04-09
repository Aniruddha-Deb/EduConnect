package com.sensei.poc.encryption;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {
	
	public static final String TRUSTSTORE_PASSWD = "public";
	public static final String TRUSTSTORE_LOC    = "src/main/resources/truststore.client";
	public static final String SERVER_IP_ADDR    = "127.0.0.1";
	public static final int    SERVER_PORT_NO    = 1132;

	public static void main( String[] args ) throws Exception {

		System.setProperty( "javax.net.ssl.trustStore", TRUSTSTORE_LOC );
		System.setProperty( "javax.net.ssl.trustStorePassword", TRUSTSTORE_PASSWD );
		System.setProperty( "javax.net.debug", "ssl" );
		
		SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		SSLSocket socket = (SSLSocket)factory.createSocket( SERVER_IP_ADDR, SERVER_PORT_NO );
		
		BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
		Scanner sc = new Scanner( System.in );
		String s = null;
		
		while( (s=sc.nextLine()) != "quit" ) {
			writer.write( s + "\n" );
			writer.flush();
		}
		sc.close();
	}	
}