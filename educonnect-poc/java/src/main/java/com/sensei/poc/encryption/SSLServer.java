package com.sensei.poc.encryption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SSLServer {
	
	public static void main(String[] args) throws IOException {
		System.setProperty( "javax.net.ssl.keyStore", "/Users/Sensei/foobar" );		
		System.setProperty( "javax.net.ssl.keyStorePassword", "foobar" );
		
		SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		SSLServerSocket ss = (SSLServerSocket)factory.createServerSocket( 1132 );
		
		SSLSocket socket = (SSLSocket)ss.accept();
		
		BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		
/*		Certificate[] certs = socket.getSession().getPeerCertificates();
		for( Certificate c : certs ) {
			System.out.println( c.getPublicKey() );
		}*/
		
		String s;
		while( (s=br.readLine()) != null ) {
			System.out.println( s );
		}
	}
}
