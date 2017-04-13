package com.educonnect.client.network.dummyserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.educonnect.common.bean.FailBean;
import com.educonnect.common.bean.payload.LoginPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.parser.Parser;
import com.educonnect.common.serializer.Serializer;

public class DummyServer {

	private static final int    SERVER_PORT_NO  = 1132;
	private static final String KEYSTORE_PASSWD = "nlcy9408";
	private static final String KEYSTORE_LOC    = "src/main/resources/server.keystore";	
	
	public static void main( String args[] ) throws Exception {
		System.setProperty( "javax.net.ssl.keyStore", KEYSTORE_LOC );
		System.setProperty( "javax.net.ssl.keyStorePassword", KEYSTORE_PASSWD );
		
		SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		SSLServerSocket serverSocket = (SSLServerSocket)factory.createServerSocket( SERVER_PORT_NO );
		
		while( true ) {
			SSLSocket sslSocket = (SSLSocket)serverSocket.accept();
			
			BufferedReader reader = new BufferedReader( new InputStreamReader( sslSocket.getInputStream() ) );
			BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( sslSocket.getOutputStream() ) );
			String s = reader.readLine();
			Payload p = Parser.parse( s );
			
			if( p instanceof LoginPayload ) {
				LoginPayload lPayload = (LoginPayload)p;
				
				if( lPayload.getGrade() != 9 ) {
					writer.write( Serializer.serialize( new FailBean( "Class currently not supported" ) ) );
					writer.flush();
					continue;
				}
				if( lPayload.getSection() != 'D' ) {
					writer.write( Serializer.serialize( new FailBean( "Division currently not supported" ) ) );
					writer.flush();
					continue;
				}
				
				System.out.println( "IN" );
				String user = DummyClientDB.getName( lPayload.getRollNo() );
				if( user == null ) {
					writer.write( Serializer.serialize( new FailBean( "You are not registered in the system" ) ) );					
					writer.flush();
					continue;
				}
				
				DummyClientModel client = new DummyClientModel( sslSocket, user );
				DummyServerClientHandler.handleClient( client );
			}
			else {
				writer.write( Serializer.serialize( new FailBean( "Bad login request" ) ) );									
				writer.flush();
			}
		}
	}
}
