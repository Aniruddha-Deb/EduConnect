package com.educonnect.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
	
	private static List<Client> clients = new ArrayList<>();
	private static BufferedReader br = null;
	private static PrintWriter    pw = null;
	
	public static void handle( Socket s ) throws IOException {
		br = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		pw = new PrintWriter( s.getOutputStream() );
		System.out.println( "init br and pw" );

		int rollNo = handleLoginRequest();
		System.out.println( rollNo );
		Client c = new Client( rollNo, s );
		clients.add( c );
		pw.flush();
		pw.close();
		br.close();
	}
	
	private static int handleLoginRequest() throws IOException {
		System.out.println( "Handling the login request" );
		String text = br.readLine();
		System.out.println( "Text = " + text );
		int rollNo = -1;
		
		if( text.startsWith( "000 >" ) ) { // login request
			rollNo = parseRollNo( text );
			
			if( rollNo < 1 ) {
				System.out.println( "Printing error" );
				pw.write( "200 > Roll number not found or lesser than specified limit" );
				pw.flush();
			}
			else {
				System.out.println( "Printing acknowledgement" );
				pw.write( "101 > Acknowledged" );
				pw.flush();
			}
		}		
		
		return rollNo;
	}
	
	private static int parseRollNo( String request ) {
		String[] parts = request.split( " " );
		String rollNoString = null;
		
		for( String p : parts ) {
			if( p.startsWith( "ROLLNO=" ) ) {
				rollNoString = p;
				break;
			}
		}
		
		parts = rollNoString.split( "=" );
		rollNoString = parts[1];
		try {
			return Integer.parseInt( rollNoString );
		} catch( Exception e ) {
			return -1;
		}
	}
}
