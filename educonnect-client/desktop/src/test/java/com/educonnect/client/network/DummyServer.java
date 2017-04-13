package com.educonnect.client.network;

import static com.educonnect.common.bean.BeanTests.CLASS;
import static com.educonnect.common.bean.BeanTests.EMAIL_ID;
import static com.educonnect.common.bean.BeanTests.IN_FILE_PATH;
import static com.educonnect.common.bean.BeanTests.PASSWORD;
import static com.educonnect.common.bean.BeanTests.ROLLNO;
import static com.educonnect.common.bean.BeanTests.SECTION;
import static com.educonnect.common.bean.BeanTests.SENDER;
import static com.educonnect.common.bean.BeanTests.TEXT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.educonnect.common.bean.AuthBean;
import com.educonnect.common.bean.FileBean;
import com.educonnect.common.bean.LoginBean;
import com.educonnect.common.bean.ShutdownBean;
import com.educonnect.common.bean.TextBean;
import com.educonnect.common.bean.payload.AuthPayload;
import com.educonnect.common.bean.payload.FilePayload;
import com.educonnect.common.bean.payload.LoginPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.ShutdownPayload;
import com.educonnect.common.bean.payload.TextPayload;
import com.educonnect.common.parser.Parser;
import com.educonnect.common.serializer.Serializer;

public class DummyServer {

	private static final int    SERVER_PORT_NO  = 1132;
	private static final String KEYSTORE_PASSWD = "nlcy9408";
	private static final String KEYSTORE_LOC    = "src/main/resources/server.keystore";	
	
	public static void main( String args[] ) throws Exception{
		System.setProperty( "javax.net.ssl.keyStore", KEYSTORE_LOC );
		System.setProperty( "javax.net.ssl.keyStorePassword", KEYSTORE_PASSWD );
		
		SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		SSLServerSocket serverSocket = (SSLServerSocket)factory.createServerSocket( SERVER_PORT_NO );
		SSLSocket socket = (SSLSocket)serverSocket.accept();
		
		BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
		
		String s = null;
		
		while( ( s=br.readLine() ) != null ) {
			System.out.println( s );
			Payload p = Parser.parse( s );

			if( p instanceof LoginPayload ) {
				writer.write( Serializer.serialize( new LoginBean( CLASS, SECTION, ROLLNO ) ) );
				writer.flush();
			}
			else if( p instanceof AuthPayload ) {
				writer.write( Serializer.serialize( new AuthBean( EMAIL_ID, PASSWORD ) ) );
				writer.flush();
			}
			else if( p instanceof TextPayload ) {
				writer.write( Serializer.serialize( new TextBean( SENDER, TEXT ) ) );
				writer.flush();
			}
			else if( p instanceof FilePayload ) {
				writer.write( Serializer.serialize( new FileBean( IN_FILE_PATH ) ) );
				writer.flush();
			}
			else if( p instanceof ShutdownPayload ) {
				System.out.println( "Shutting down" ); 
				writer.write( Serializer.serialize( new ShutdownBean() ) );
				writer.flush();
				break;
			}
			writer.flush();
		}

	}

}
