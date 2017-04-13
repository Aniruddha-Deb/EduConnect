package com.educonnect.client.network.dummyserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.InfoBean;
import com.educonnect.common.serializer.Serializer;

public class DummyClientModel {

	private BufferedWriter writer = null;
	private String name = null;
	
	public DummyClientModel( Socket socket, String name ) {
		BufferedReader reader = null;
		this.name = name;
		try {
			this.writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
			reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		DummyClientReceiver receiver = new DummyClientReceiver( reader, this );
		new Thread( receiver, "Receiver" ).start();
		
		send( new InfoBean( name ) );
	}
	
	public String getName() {
		return name;
	}
	
	public void send( String s ) {
		System.out.println( "Sending to" + name + ", " + s );		
		try {
			writer.write( s + "\n" );
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send( Bean b ) {
		String s = Serializer.serialize( b );
		System.out.println( "Sending " + s );
		try {
			writer.write( s );
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println( "sent" );
	}

	public void receive( String s ) {
		DummyServerClientHandler.broadcast( s, this );
	}
	
	public void shutDown() {
		DummyServerClientHandler.remove( this );
	}
}
