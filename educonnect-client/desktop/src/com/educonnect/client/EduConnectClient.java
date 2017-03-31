package com.educonnect.client;

import java.io.IOException;
import java.net.UnknownHostException;

import com.educonnect.client.adapter.SocketNetworkAdapter;
import com.educonnect.common.beans.LoginBean;

public class EduConnectClient {

	public static void main(String[] args) throws UnknownHostException, IOException {

		LoginBean loginBean = new LoginBean( 11, "Hiii", null );
		SocketNetworkAdapter adapter = new SocketNetworkAdapter( "127.0.0.1", 1132 );
		
		try {
			adapter.send( loginBean );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
				
	}
}
