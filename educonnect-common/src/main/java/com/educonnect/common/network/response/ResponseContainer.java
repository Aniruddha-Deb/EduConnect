package com.educonnect.common.network.response;

import com.educonnect.common.message.core.Response;

public class ResponseContainer {

	private Response response = null;

	public synchronized void setResponse( Response r ) {
		this.response = r;
		this.notifyAll();
	}
	
	public synchronized Response waitForResponse() throws InterruptedException{
		while( response == null ) {
			this.wait();
		}
		return response;
	}
}
