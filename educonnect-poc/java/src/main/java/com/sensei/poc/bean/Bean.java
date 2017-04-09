package com.sensei.poc.bean;

import com.sensei.poc.bean.header.BeanHeader;
import com.sensei.poc.bean.payload.JSONPayload;
import com.sensei.poc.json.JSONSerializer;

public abstract class Bean {
	
	protected BeanHeader header = null;
	protected JSONPayload payload = null;
	
	@Override
	public String toString() {
		return header.toString() + "\n" + JSONSerializer.serialize( payload );
	}

}
