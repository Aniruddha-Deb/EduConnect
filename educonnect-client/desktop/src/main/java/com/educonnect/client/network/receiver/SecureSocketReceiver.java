package com.educonnect.client.network.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;

import com.educonnect.client.network.SecureSocketNetworkAdapter;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.ShutdownPayload;
import com.educonnect.common.parser.Parser;

public class SecureSocketReceiver implements Receiver {

	private BufferedReader reader = null;
	private SSLSocket sslSocket = null;
	private SecureSocketNetworkAdapter adapter = null;
	
	public SecureSocketReceiver( SSLSocket sslSocket, SecureSocketNetworkAdapter adapter ) {
		this.adapter = adapter;
		this.sslSocket = sslSocket;
		try {
			this.reader = new BufferedReader( new InputStreamReader( sslSocket.getInputStream() ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		Payload p;
		try {		
			String s = reader.readLine();
			p = Parser.parse( s );
			
			while( !( p instanceof ShutdownPayload ) ) {
				adapter.receive( p );
				s = reader.readLine();
				p = Parser.parse( s );
			}
			System.out.println( "Closing" );
			sslSocket.close();
			reader.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
