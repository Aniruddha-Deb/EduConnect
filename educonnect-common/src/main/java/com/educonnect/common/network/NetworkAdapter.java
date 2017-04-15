package com.educonnect.common.network;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.payload.Payload;

/**
 * The NetworkAdapter interface is an interface which needs to be implemented by 
 * any class which sends or receives data over the network. The classes implementing 
 * this need to provide their own custom ways of sending data eg: HTTP, Sockets etc. 
 * and implement those functions in the statements specified. 
 * 
 * @author Sensei
 *
 */
public interface NetworkAdapter {

	public void send( Bean b );
	
	public void receive( Payload p );
	
	public void shutdown();
}
