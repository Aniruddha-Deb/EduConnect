package com.educonnect.server.engine;

import com.educonnect.server.network.SecureSocketServerNetworkAdapter;
import com.educonnect.server.network.ServerNetworkAdapter;

public class ServerEngine {

	private ServerNetworkAdapter serverAdapter = null; 
	
	public ServerEngine() {
		serverAdapter = new SecureSocketServerNetworkAdapter( "src/main/resources/server.keystore", 
															  "nlcy9408", 
															  1132 );
	}
	
	public void start() {
		serverAdapter.receiveClients();
	}
}
