package com.educonnect.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{

	private Credentials credentials = null;
	private Socket s = null;
	
	private BufferedReader clientReader = null;
	private PrintWriter    clientWriter = null;

	public Client( int rollNo, Socket s ) {
		credentials = new Credentials( rollNo );
		
		try {
			clientReader = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		try {
			clientWriter = new PrintWriter( s.getOutputStream() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		run();
	}
	
	@Override
	public void run() {
		try {
			while( s.isConnected() ) {
				
				String request = null;
				if( clientReader.ready() ) {
					request = clientReader.readLine();
				}
			}
		} 
		catch( Exception ex ) {
			ex.printStackTrace();
		}
		finally {
			try {
				clientReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			clientWriter.close();
		}
	}
}

