package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.Payload;

public class Bean {
	
	private Header  header = null;
	private Payload payload = null;

	public Bean( Header header, Payload payload ) {
		this.header  = header;
		this.payload = payload;
	}
	
	public Header getHeader() {
		return header;
	}
	
	public Payload getPayload() {
		return payload;
	}
}
