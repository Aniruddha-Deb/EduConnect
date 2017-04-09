package com.sensei.poc.bean;

import com.sensei.poc.bean.header.BeanHeader;
import com.sensei.poc.bean.payload.JSONPayload;
import com.sensei.poc.json.JSONSerializer;

public class Bean {
	
	private BeanHeader header = null;
	private JSONPayload payload = null;

	public Bean( BeanHeader header, JSONPayload payload ) {
		this.header = header;
		this.payload = payload;
	}
	
	public BeanHeader getHeader() {
		return header;
	}
	
	public JSONPayload getPayload() {
		return payload;
	}
	
	@Override
	public String toString() {
		return header.toString() + "\n" + JSONSerializer.serialize( payload );
	}

}
