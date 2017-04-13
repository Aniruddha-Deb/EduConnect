package com.sensei.poc.network;

import com.educonnect.common.bean.LoginBean;

public class NetworkAdapterTest {

	public static void main( String[] args ) {

		SecureSocketNetworkAdapter adapter = new SecureSocketNetworkAdapter( "127.0.0.1", 1132 );
		adapter.send( new LoginBean( 9, 'D', 11 ) );
		adapter.send( new LoginBean( 9, 'D', 11 ) );
		adapter.send( new LoginBean( 9, 'D', 11 ) );
		adapter.send( new LoginBean( 9, 'D', 11 ) );
		adapter.shutdown();
	}

}
