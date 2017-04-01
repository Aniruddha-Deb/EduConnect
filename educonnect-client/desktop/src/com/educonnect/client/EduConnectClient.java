package com.educonnect.client;

import java.util.Scanner;

import com.educonnect.common.adapter.SocketNetworkAdapter;
import com.educonnect.common.beans.LoginBean;
import com.educonnect.common.beans.TextBean;

public class EduConnectClient {

	public static void main( String[] args ) throws Exception {

		LoginBean loginBean = new LoginBean( 37, "Hiii", null );
		SocketNetworkAdapter adapter = new SocketNetworkAdapter( "127.0.0.1", 1132 );
		
		try {
			adapter.send( loginBean );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		
		Scanner sc = new Scanner( System.in );
		while( true ) {
			String s = sc.nextLine();
			adapter.send( new TextBean( s ) );
			
			if( s.equals( "quit" ) ) {
				sc.close();
				break;
			}
		}
	}
}
