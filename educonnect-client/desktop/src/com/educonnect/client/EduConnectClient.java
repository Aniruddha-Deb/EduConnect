package com.educonnect.client;

import java.util.Scanner;

import com.educonnect.common.adapter.SocketNetworkAdapter;
import com.educonnect.common.beans.Bean;
import com.educonnect.common.beans.Bean.BeanConstants;

public class EduConnectClient {

	public static void main( String[] args ) throws Exception {

		Scanner sc = new Scanner( System.in );
		System.out.print( "Enter your roll number: " );
		String rollNo = "ROLLNO=" + sc.nextInt();
		
		Bean loginBean = new Bean( BeanConstants.LOGIN, rollNo );
		
		SocketNetworkAdapter adapter = new SocketNetworkAdapter( "127.0.0.1", 1132 );
		
		try {
			adapter.send( loginBean );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		
		Thread t = new Thread( new Runnable() {
			
			@Override
			public void run() {
				while( true ) {
					Bean b = null;
					try {
						b = adapter.receive();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if( b.getHeader().equals( BeanConstants.TEXT ) ) {						
						System.out.println( (String)b.getPayload() );
					}
				}
			}
		} );
		
		t.start();
		
		while( true ) {
			String s = sc.nextLine();
			adapter.send( new Bean( BeanConstants.TEXT, s ) );
			
			if( s.equals( "quit" ) ) {
				sc.close();
				break;
			}
		}
	}
}
