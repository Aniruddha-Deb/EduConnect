package com.educonnect.common.adapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.educonnect.common.beans.Bean;

/**
 * 
 * The SocketNetworkAdapter class acts as a concrete implementation of the 
 * NetworkAdapter class for sockets. It helps in transferring beans using 
 * standard TCP/IP sockets from client to server and vice versa. It has two 
 * methods: Send and Receive. Send is used to send a bean through the pipe 
 * whereas receive is used to receive a bean through the pipe. 
 * 
 * @author Sensei
 *
 */
public class SocketNetworkAdapter extends NetworkAdapter{
	
	private Socket socket = null;

	/**
	 * Constructor for this class. Accepts an IP address and a port to initialize 
	 * the instance socket.
	 * 
	 * @param ipAddress The IP address of the server
	 * @param port The target port on which the corresponding ServerSocket listener 
	 * is based
	 */
	public SocketNetworkAdapter( String ipAddress, int port ) {
		try {
			socket = new Socket( ipAddress, port );
		} catch ( UnknownHostException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A simplified constructor which directly accepts a socket and assigns the 
	 * instance socket to the accepted socket
	 *  
	 * @param socket The target socket, connected and ready to send / receive data
	 */
	public SocketNetworkAdapter( Socket socket ) {
		this.socket = socket;
	} 
	
	@Override
	public void send( Bean b ) {
		
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( socket.getOutputStream() );
			oos.writeObject( b );
			oos.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Bean receive() {
		
		ObjectInputStream ois;
		Bean b = null;
		
		try {
			ois = new ObjectInputStream( socket.getInputStream() );
			b = (Bean)ois.readObject();
		} catch ( IOException e ) {
			e.printStackTrace();
		} catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		
		return b; 
	}
}
