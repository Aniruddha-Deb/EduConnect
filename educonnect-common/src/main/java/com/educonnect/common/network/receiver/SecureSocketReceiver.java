package com.educonnect.common.network.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;

import com.educonnect.common.message.core.Response;
import com.educonnect.common.message.shutdown.ShutdownResponse;
import com.educonnect.common.network.SecureSocketNetworkAdapter;
import com.educonnect.common.parser.Parser;

/**
 * The SecureSocketReceiver is a receiver which collects data sent over a secure 
 * socket. The secure socket is required to implement the TLS protocol to securely 
 * transfer data. 
 * 
 * @author Sensei 
 */
public class SecureSocketReceiver implements Runnable {

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
		
		Response r = null;
		try {		
			r = (Response)Parser.parse( readHeader(), readPayload() );
			
			while( !( r instanceof ShutdownResponse ) ) {
				adapter.putResponse( r );
				r = (Response)Parser.parse( readHeader(), readPayload() );
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private String readHeader() {
		String header = null;
		try {
			int headerLength = Integer.parseInt( reader.readLine() );
			header = new String();
			for( int i=0; i<headerLength; i++ ) {
				char charRead = (char)reader.read();
				header += charRead;
			}
			reader.readLine();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		return header;
	}
	
	private String readPayload() {
		String payload = null;
		try {
			int payloadLength = Integer.parseInt( reader.readLine() );
			payload = new String();
			for( int i=0; i<payloadLength; i++ ) {
				char charRead = (char)reader.read();
				payload += charRead;
			}
			reader.readLine();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		return payload;
	}
}
