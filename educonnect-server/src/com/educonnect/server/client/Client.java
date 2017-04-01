package com.educonnect.server.client;

import com.educonnect.common.adapter.SocketNetworkAdapter;
import com.educonnect.common.beans.Bean;

public class Client extends Thread{

	private Credentials credentials = null;
	private SocketNetworkAdapter adapter = null;
	
	public Client( int rollNo, SocketNetworkAdapter adapter ) {
		credentials = new Credentials( rollNo );		
		this.adapter = adapter;
	}
	
	public SocketNetworkAdapter getAdapter() {
		return adapter;
	}
	
	@Override
	public void run() {
		
		try {
			Bean textBean;
			while( (textBean=adapter.receive()) != null ) {
				System.out.println( credentials.getRollNo() + "> " + (String)textBean.getPayload() );
				ClientHandler.broadcast( textBean, this );
			}
		}
		catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
}

