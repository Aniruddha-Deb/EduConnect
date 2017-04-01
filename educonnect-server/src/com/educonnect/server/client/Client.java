package com.educonnect.server.client;

import com.educonnect.common.adapter.SocketNetworkAdapter;
import com.educonnect.common.beans.TextBean;

public class Client extends Thread{

	private Credentials credentials = null;
	private SocketNetworkAdapter adapter = null;
	
	public Client( int rollNo, SocketNetworkAdapter adapter ) {
		credentials = new Credentials( rollNo );		
		this.adapter = adapter;
	}
	
	@Override
	public void run() {
		
		try {
			TextBean tb;
			while( (tb=(TextBean)adapter.receive()) != null ) {
				System.out.println( credentials.getRollNo() + "> " + tb.getData() );
			}
		}
		catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
}

