package com.educonnect.common.network;

import com.educonnect.common.message.core.Request;
import com.educonnect.common.message.core.Response;

public interface NetworkAdapter {

	public void sendAsync( Request r );
	
	public Response send( Request r );
	
	public void shutdown();
}
