package com.educonnect.common.network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.ShutdownBean;
import com.educonnect.common.network.receiver.Receiver;
import com.educonnect.common.network.sender.Sender;

public class NetworkAdapter {
	
	private BlockingQueue<Bean> sentBeans     = null;
	private BlockingQueue<Bean> receivedBeans = null;

	private Sender   sender   = null;
	private Receiver receiver = null;
	
	public NetworkAdapter() {
		sentBeans     = new LinkedBlockingQueue<>();
		receivedBeans = new LinkedBlockingQueue<>();
		
		sender   = new Sender( sentBeans );
		receiver = new Receiver( receivedBeans );
		
		Thread senderThread   = new Thread( sender, "Sender" );
		Thread receiverThread = new Thread( receiver, "Receiver" );
		
		senderThread.start();
		receiverThread.start();
	}
	
	public void sendBean( Bean bean ) {
		try {
			sentBeans.put( bean );
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}
	}
	
	public Bean receiveBean() {
		Bean bean = null;
		try {
			bean = receivedBeans.take();
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public void shutDown() {
		try {
			sentBeans.put( new ShutdownBean() );
			receivedBeans.put( new ShutdownBean() );
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}
	}
}
