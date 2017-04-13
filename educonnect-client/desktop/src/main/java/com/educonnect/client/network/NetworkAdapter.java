package com.educonnect.client.network;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.payload.Payload;

public interface NetworkAdapter {

	public void send( Bean b );
	
	public void receive( Payload p );
	
	public void shutdown();
}
