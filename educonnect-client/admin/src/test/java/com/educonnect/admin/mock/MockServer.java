package com.educonnect.admin.mock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.educonnect.common.bean.DatabaseBean;
import com.educonnect.common.bean.FailBean;
import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.bean.ShutdownBean;
import com.educonnect.common.bean.payload.FilePayload;
import com.educonnect.common.bean.payload.InfoPayload;
import com.educonnect.common.bean.payload.LoginPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.ShutdownPayload;
import com.educonnect.common.parser.Parser;
import com.educonnect.common.serializer.Serializer;

public class MockServer {
	
	public static void main(String[] args) throws IOException {
		
		System.setProperty( "javax.net.ssl.keyStore", "src/test/resources/server.keystore" );
		System.setProperty( "javax.net.ssl.keyStorePassword", "nlcy9408" );
		
		SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		SSLServerSocket serverSocket = (SSLServerSocket)factory.createServerSocket( 1132 );
		SSLSocket socket = (SSLSocket)serverSocket.accept();
		System.out.println( "Received socket" );
		BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
		
		String s = null;
				
		while( ( s=br.readLine() ) != null ) {
			
			Payload p = Parser.parse( s );
			
			if( p instanceof LoginPayload ) {
				LoginPayload l = (LoginPayload)p;
				if( l.getEmailId().equals( "aniruddha.deb.2002@gmail.com" ) && 
					new String( l.getPassword() ).equals( "a" ) ) {
					writer.write( Serializer.serialize( new InfoBean( "Aniruddha Deb" ) ) );
					writer.flush();
					
					writer.write( Serializer.serialize( new InfoBean( "DB headers:9A 10A 10B" ) ) );
					writer.flush();
					
				}
				else {
					writer.write( Serializer.serialize( new FailBean( "Admin not registered" ) ) );
					writer.flush();
				}
			}
			else if( p instanceof FilePayload ) {
				((FilePayload) p).unloadFile( "src/test/resources/sheet.xlsx" );
			}
			else if( p instanceof InfoPayload ) {
				if( ((InfoPayload) p).getInfo().endsWith( "9A" ) ) {
					System.out.println( "Sending 9A payload" ); 
					writer.write( Serializer.serialize( new DatabaseBean( new String[]{"rollNo", "firstName", "lastName"} , 
							 new String[][]{
								{ "1", "Kushant", "Agarwal" }
							 } ) ) );
					writer.flush();
				}
				else {
					System.out.println( "Sending fat payload" ); 
					writer.write( Serializer.serialize( new DatabaseBean( new String[]{"rollNo", "firstName", "lastName"} , 
							 new String[][]{
								{ "2", "Krish", "Anand" }
							 } ) ) );					
					writer.flush();
				}
			}
			else if( p instanceof ShutdownPayload ) {
				writer.write( Serializer.serialize( new ShutdownBean() ) );
				writer.flush();
				break;
			}
		}
	}
}
