package com.sensei.poc.network;

import com.educonnect.common.message.Bean;
import com.educonnect.common.message.payload.Payload;

public interface NetworkAdapter {

	public void send( Bean b );
	
	public void receive( Payload p );
	
	public void shutdown();
}
