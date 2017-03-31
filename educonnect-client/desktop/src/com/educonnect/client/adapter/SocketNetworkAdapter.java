package com.educonnect.client.adapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.educonnect.common.beans.Bean;
import com.educonnect.common.beans.ImageBean;
import com.educonnect.common.beans.LoginBean;
import com.educonnect.common.beans.OKBean;
import com.educonnect.common.beans.StatusBean;
import com.educonnect.common.beans.TextBean;

public class SocketNetworkAdapter implements NetworkAdapter{
	
	private Socket socket = null;
	
	public SocketNetworkAdapter( String ipAddress, int port ) throws UnknownHostException, IOException {
		
		socket = new Socket( ipAddress, port );
	}
	
	@Override
	public void send( Bean b ) throws Exception {
		
		ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );
		oos.writeObject( b );
		oos.flush();
		oos.close();
	}
	
	@Override
	public Bean receive() throws Exception {
		
		ObjectInputStream ois = new ObjectInputStream( socket.getInputStream() );
		Bean b = (Bean)ois.readObject();
		ois.close();
		
		switch( b.getHeader() ) {
		
			case TEXT:
				return (TextBean)b;

			case OK:
				return (OKBean)b;
				
			case SUCCESS:
				return (StatusBean)b;
				
			case FAILURE:
				return (StatusBean)b;
				
			case IMAGE:
				return (ImageBean)b;
				
			case LOGIN:
				return (LoginBean)b;
		}
		return null;
	}
}
