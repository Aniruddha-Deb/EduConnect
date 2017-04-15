package com.sensei.poc.encryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.bean.ShutdownBean;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.ShutdownPayload;
import com.educonnect.common.parser.Parser;
import com.educonnect.common.serializer.Serializer;

public class SSLServer {

	
	public static final int    SERVER_PORT_NO    = 1132;
	public static final String KEYSTORE_PASSWD = "EduConnect";
	public static final String KEYSTORE_LOC    = "src/main/resources/keystore.server";

	public static void main( String[] args ) throws Exception {

		System.setProperty( "javax.net.ssl.keyStore", KEYSTORE_LOC );
		System.setProperty( "javax.net.ssl.keyStorePassword", KEYSTORE_PASSWD );
		
		SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		SSLServerSocket serverSocket = (SSLServerSocket)factory.createServerSocket( SERVER_PORT_NO );
		SSLSocket socket = (SSLSocket)serverSocket.accept();
		
		BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
		String s = null;
		
		while( (s=br.readLine()) != null ) {
			Payload p = Parser.parse( s );
			writer.write( Serializer.serialize( new InfoBean( "looo" ) ) );
			writer.flush();
			if( p instanceof ShutdownPayload ) {
				System.out.println( "Shutting down" ); 
				writer.write( Serializer.serialize( new ShutdownBean() ) );
				writer.flush();
				break;
			}
		}
	}
}
