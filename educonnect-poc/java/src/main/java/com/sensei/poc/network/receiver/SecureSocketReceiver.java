package com.sensei.poc.network.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;

import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.ShutdownPayload;
import com.educonnect.common.parser.Parser;
import com.sensei.poc.network.SecureSocketNetworkAdapter;

public class SecureSocketReceiver implements Receiver {

	private BufferedReader reader = null;
	private SecureSocketNetworkAdapter adapter = null;
	
	public SecureSocketReceiver( SSLSocket sslSocket, SecureSocketNetworkAdapter adapter ) {
		this.adapter = adapter;
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
			System.out.println( s );
			p = Parser.parse( s );
			System.out.println( "Got p" );
			
			while( !( p instanceof ShutdownPayload ) ) {
				adapter.receive( p );
				s = reader.readLine();
				p = Parser.parse( s );
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
