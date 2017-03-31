package com.educonnect.client.adapter;

import com.educonnect.common.beans.Bean;

interface NetworkAdapter {

	public void send( Bean b ) throws Exception;
	
	public Bean receive() throws Exception;
}
