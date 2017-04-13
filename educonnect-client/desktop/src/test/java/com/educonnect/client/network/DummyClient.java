package com.educonnect.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.educonnect.common.bean.LoginBean;
import com.educonnect.common.bean.TextBean;
import com.educonnect.common.bean.payload.FailPayload;
import com.educonnect.common.bean.payload.InfoPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.TextPayload;

public class DummyClient {

	
	public static void main( String[] args ) throws Exception {
		SecureSocketNetworkAdapter adapter = new SecureSocketNetworkAdapter( "127.0.0.1", 1132 );

		Scanner sc = new Scanner( System.in );
		
		System.out.print( "Class: " );
		int grade = sc.nextInt();
		System.out.print( "Section: " );
		char section = sc.next().charAt(0);
		System.out.print( "Roll no.: " );
		int rollNo = sc.nextInt();
		
		adapter.send( new LoginBean( grade, section, rollNo ) );
		Payload p = adapter.get();
		
		String name = null;
		if( p instanceof FailPayload ) {
			System.out.println( ((FailPayload) p).getCause() );
			adapter.shutdown();
			System.exit( -1 );
		}
		else if( p instanceof InfoPayload ) {			
			name = ((InfoPayload) p).getInfo();
		}
		
		System.out.println( "EduConnect v.0.1 pre-alpha" );
		System.out.println( "Logged in as " + name );
		System.out.println( "Press q to quit" );
		System.out.println( "---------------------------------" );
		System.out.print( "> " );
		
		String s = sc.nextLine();
		// hacky solution to a horrible bug
		if( s.equals( "" ) ) {
			s = sc.nextLine();
		}
		while( !( s.equals( "q" ) ) ) {
			adapter.send( new TextBean( name, s ) );
			System.out.print( "> " );
			s = sc.nextLine();
		}
		
		adapter.shutdown();
		System.out.println( "Shutdown" );
		sc.close();
	}

	public static void updateUI( Payload p ) {
		try {
			System.out.println( ((TextPayload) p).getSender() + "> " + ((TextPayload)p).getText() );
		} catch( ClassCastException e ) {
			// swallow
		}
	}
}
