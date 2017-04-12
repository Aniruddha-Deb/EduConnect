package com.educonnect.common.network.sender;

import java.util.concurrent.BlockingQueue;

import com.educonnect.common.bean.Bean;

public class Sender implements Runnable {

	private BlockingQueue<Bean> sentBeans = null;
	
	public Sender( BlockingQueue<Bean> sentBeans ) {
		this.sentBeans = sentBeans;
	}
	
	@Override
	public void run() {
		// To be overriden by subclasses with kiss of death pattern
	}
	
	public BlockingQueue<Bean> getSentBeans() {
		return sentBeans;
	}
}
