package com.sensei.poc.encryption;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory; 

public class SSLClient {

	public static void main( String[] args ) throws UnknownHostException, IOException {
		System.setProperty( "javax.net.ssl.trustStore", "/Users/Sensei/foobar" );		
		System.setProperty( "javax.net.ssl.trustStorePassword", "foobar" );
		
		SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		SSLSocket socket = (SSLSocket)factory.createSocket( "127.0.0.1", 1132 );

		Scanner sc = new Scanner( System.in );
		PrintWriter pw = new PrintWriter( socket.getOutputStream() );
		
		String[] ciphers = socket.getEnabledCipherSuites();
		String c = socket.getSession().getProtocol();
		System.out.println( "Protocol = " + c );
		
		for( String s : ciphers ) {
			System.out.println( s );
		}
		
		String s;
		while( !(s=sc.nextLine()).equals( "quit" ) ) {
			pw.println( s );
			pw.flush();
		}
		s = null;
		pw.println( s );
		pw.flush();
		sc.close();
	}	
}