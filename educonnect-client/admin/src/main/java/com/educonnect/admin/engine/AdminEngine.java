package com.educonnect.admin.engine;

import com.educonnect.admin.ui.MainFrame;
import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.engine.Engine;
import com.educonnect.common.network.SecureSocketNetworkAdapter;

public class AdminEngine extends Engine{

	private MainFrame mainFrame = null;
	private SecureSocketNetworkAdapter adapter = null;
	
	public AdminEngine() {
		
	}
	
	@Override
	public void handle( Payload p ) {
		
	}
	
	public void send( Bean b ) {
		adapter.send( b );
	}
	
	@Override
	public void start() {
	}

	@Override
	public void shutdown() {
	}
}
