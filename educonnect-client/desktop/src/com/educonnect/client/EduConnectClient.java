package com.educonnect.client;

import java.util.Scanner;

import com.educonnect.common.adapter.NetworkAdapter;
import com.educonnect.common.adapter.SocketNetworkAdapter;
import com.educonnect.common.beans.Bean;
import com.educonnect.common.beans.Bean.Header;

public class EduConnectClient {

	Scanner sc = null;
	NetworkAdapter adapter = null;
	
	public EduConnectClient( String serverIpAddress, int serverPort ) {
		sc = new Scanner( System.in );
		adapter = new SocketNetworkAdapter( serverIpAddress, serverPort );
	}
	
	public static void main( String[] args ) throws Exception {

		Bean loginBean = new Bean( Header.LOGIN, rollNo );
		
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
					
					if( b.getHeader().equals( Header.TEXT ) ) {						
						System.out.println( (String)b.getPayload() );
					}
				}
			}
		} );
		
		t.start();
		
		while( true ) {
			String s = sc.nextLine();
			adapter.send( new Bean( Header.TEXT, s ) );
			
			if( s.equals( "quit" ) ) {
				sc.close();
				break;
			}
		}
	}
}
