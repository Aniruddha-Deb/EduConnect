package com.educonnect.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.educonnect.server.client.ClientHandler;

public class EduConnectServer {
	
	ServerSocket ss = null;
	
	public EduConnectServer() throws IOException{
		ss = new ServerSocket( 1132 );
	}
	
	public void run() throws IOException{
		System.out.println( "started" );
		while( true ) {
			Socket s = ss.accept();
			System.out.println( "Somebody joined!" );
			ClientHandler.handle( s );
		}
	}

	public static void main( String[] args ) {
		try {
			new EduConnectServer().run();
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
}
