package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.ShutdownPayload;

public class ShutdownBean extends Bean {
	
	public ShutdownBean() {
		super( Header.SHUTDOWN, new ShutdownPayload() );
	}

}
