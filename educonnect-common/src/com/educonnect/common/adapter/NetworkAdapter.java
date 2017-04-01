package com.educonnect.common.adapter;

import com.educonnect.common.beans.Bean;

/**
 * 
 * A NetworkAdapter is any class which extends this class and abstracts away the 
 * intricacies involved in transferring data between the client and server. The 
 * client and server, instead of dealing with bare network connections, create 
 * a NetworkAdapter which handles reading and writing data across the network. 
 * Thus, the classes using a NetworkAdapter do not care about the protocol on 
 * which the connection is carried out. Different protocols eg. 
 * HTTP, TCP/IP sockets, must extend this class and override the send and 
 * receive methods and implement their own methods of writing and reading beans 
 * across the particular protocol.
 *   
 * @author Sensei
 *
 */
public abstract class NetworkAdapter {

	/**
	 * The send function is used to write one single bean across the network. This 
	 * function has to be overriden by the subclasses and must implement their 
	 * protocol and method of writing the bean across the network.  
	 * 
	 * @param b The bean to be written across the network
	 */
	public void send( Bean b ) {}

	/**
	 * The receive function is used to receive one single bean across the network. 
	 * This function has to be overriden by the subclasses and must implement their 
	 * protocol and method of writing the bean across the network.
	 * 
	 * @return One single bean, received from the other side.
	 */
	public Bean receive() { return null; }
}
