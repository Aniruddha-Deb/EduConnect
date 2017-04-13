package com.educonnect.client.network;

import java.util.Scanner;

import com.educonnect.common.bean.LoginBean;
import com.educonnect.common.bean.TextBean;
import com.educonnect.common.bean.payload.InfoPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.TextPayload;

public class DummyClient {

	private static Scanner sc = new Scanner( System.in );
	
	public static void main( String[] args ) throws InterruptedException{
		SecureSocketNetworkAdapter adapter = new SecureSocketNetworkAdapter( "127.0.0.1", 1132 );
		String s ;

		int grade = getGrade();
		char section = getSection();
		int rollNo = getRollNo();
		
		adapter.send( new LoginBean( grade, section, rollNo ) );
		InfoPayload p = (InfoPayload)adapter.get();
		String name = p.getInfo();
		
		System.out.println( "EduConnect v.0.1 pre-alpha" );
		System.out.println( "Logged in as " + name );
		System.out.println( "Press q to quit" );
		System.out.println( "---------------------------------" );
		System.out.print( "> " );
		
		s = sc.nextLine();
		while( !(s.equals( "q" )) ) {
			adapter.send( new TextBean( name, s ) );
			s = sc.nextLine();
		}
		
		adapter.shutdown();
		System.out.println( "Shutdown" );
		sc.close();
	}

	private static int getRollNo() {
		System.out.print( "Roll no: " );
		return sc.nextInt();
	}

	private static char getSection() {
		System.out.print( "Section: " );
		return sc.next().toCharArray()[0];
	}

	private static int getGrade() {
		System.out.print( "Class: " );
		return sc.nextInt();
	}
	
	public static void updateUI( Payload p ) {
		try {
			System.out.println( ((TextPayload) p).getSender() + "> " + ((TextPayload)p).getText() );
		} catch( ClassCastException e ) {
			// swallow
		}
	}
}
