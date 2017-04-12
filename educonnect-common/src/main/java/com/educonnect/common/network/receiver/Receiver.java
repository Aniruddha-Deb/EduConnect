package com.educonnect.common.network.receiver;

import java.util.concurrent.BlockingQueue;

import com.educonnect.common.bean.Bean;

public class Receiver implements Runnable {

	private BlockingQueue<Bean> receivedBeans = null;
	
	public Receiver( BlockingQueue<Bean> receivedBeans ) {
		this.receivedBeans = receivedBeans;
	}
	
	@Override
	public void run() {
		// To be overriden by subclasses with kiss of death pattern
	}
	
	public BlockingQueue<Bean> getReceivedBeans() {
		return receivedBeans;
	}
}
