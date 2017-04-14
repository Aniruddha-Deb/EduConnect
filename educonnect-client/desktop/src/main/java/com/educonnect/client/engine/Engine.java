package com.educonnect.client.engine;

import com.educonnect.client.network.NetworkAdapter;
import com.educonnect.client.network.SecureSocketNetworkAdapter;
import com.educonnect.client.ui.MainFrame;
import com.educonnect.common.bean.payload.Payload;

public class Engine {
	
	private static final String IP_ADDRESS = "127.0.0.1";
	private static final int    PORT       = 1132;
	
	private MainFrame mainFrame = null;
	private NetworkAdapter adapter = null;
	
	public Engine() {
		mainFrame = new MainFrame();
		mainFrame.display();
		//adapter = new SecureSocketNetworkAdapter( IP_ADDRESS, PORT, this );
	}
	
	public void updateUI( Payload p ) {
		mainFrame.updateUI( p );
	}
}
