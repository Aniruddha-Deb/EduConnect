package com.educonnect.admin.mock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.educonnect.common.bean.FailBean;
import com.educonnect.common.bean.FileBean;
import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.bean.ShutdownBean;
import com.educonnect.common.bean.payload.FilePayload;
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
		
		BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
		String s = null;
		
		while( ( s=br.readLine() ) != null ) {
			
			Payload p = Parser.parse( s );

			if( p instanceof LoginPayload ) {
				LoginPayload l = (LoginPayload)p;
				if( l.getEmailId().equals( "aniruddha.deb.2002@gmail.com" ) && 
					l.getPassword().equals( "an1ruddha" ) ) {
					writer.write( Serializer.serialize( new InfoBean( "Aniruddha Deb" ) ) );
					writer.flush();
					File file = new File( "src/test/resources/sheet.xlsx" );
					if( !file.exists() ) {
						file.createNewFile();
					}
					writer.write( Serializer.serialize( new FileBean( "src/test/resources/sheet.xlsx" ) ) );
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
			else if( p instanceof ShutdownPayload ) {
				writer.write( Serializer.serialize( new ShutdownBean() ) );
				writer.flush();
				break;
			}
		}
	}
}
