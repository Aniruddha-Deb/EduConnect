package com.educonnect.client.network.sender;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.BlockingQueue;

import javax.net.ssl.SSLSocket;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.ShutdownBean;
import com.educonnect.common.serializer.Serializer;

public class SecureSocketSender implements Sender {

	private BufferedWriter writer = null;
	private BlockingQueue<Bean> sentQueue = null;
	
	public SecureSocketSender( SSLSocket sslSocket, BlockingQueue<Bean> sentQueue ) {
		this.sentQueue = sentQueue;
		try {
			this.writer = new BufferedWriter( new OutputStreamWriter( sslSocket.getOutputStream() ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {

		Bean b;
		try {
			b = sentQueue.take();
			while( true ) {
				String stringToSend = Serializer.serialize( b );
				writer.write( stringToSend );
				writer.flush();
				if( b instanceof ShutdownBean ) {
					System.out.println( "Shutting down" );
					break;
				}
				b = sentQueue.take();
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
