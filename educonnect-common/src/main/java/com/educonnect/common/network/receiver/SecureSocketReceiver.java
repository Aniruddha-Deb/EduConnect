package com.educonnect.common.network.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;

import com.educonnect.common.message.core.Response;
import com.educonnect.common.message.shutdown.ShutdownResponse;
import com.educonnect.common.network.NetworkUtils;
import com.educonnect.common.network.SecureSocketNetworkAdapter;

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
			r = (Response)NetworkUtils.readMessage( reader );
			
			while( !( r instanceof ShutdownResponse ) ) {
				adapter.putResponse( r );
				r = (Response)NetworkUtils.readMessage( reader );
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}	
}
