package com.educonnect.common.adapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.educonnect.common.beans.Bean;

public class SocketNetworkAdapter implements NetworkAdapter{
	
	private Socket socket = null;
	
	public SocketNetworkAdapter( String ipAddress, int port ) throws UnknownHostException, IOException {
		socket = new Socket( ipAddress, port );
	}
	
	public SocketNetworkAdapter( Socket socket ) {
		this.socket = socket;
	} 
	
	@Override
	public void send( Bean b ) throws Exception {
		
		ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );
		oos.writeObject( b );
		oos.flush();
	}
	
	@Override
	public Bean receive() throws Exception {
		
		ObjectInputStream ois = new ObjectInputStream( socket.getInputStream() );
		Bean b = (Bean)ois.readObject();
		
		return b; 
	}
}
