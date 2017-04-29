package com.educonnect.server.engine;

import com.educonnect.server.Constants;
import com.educonnect.server.network.SecureSocketServerNetworkAdapter;
import com.educonnect.server.network.ServerNetworkAdapter;

public class ServerEngine {

	private ServerNetworkAdapter serverAdapter = null; 
	
	public ServerEngine() {
		serverAdapter = new SecureSocketServerNetworkAdapter( 
								Constants.KEYSTORE_LOC, 
								Constants.KEYSTORE_PASSWD,
								Constants.SERVER_PORT       );
	}
	
	public void start() {
		serverAdapter.receiveClients();
	}
}
