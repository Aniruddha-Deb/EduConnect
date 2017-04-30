package com.educonnect.common.message.info;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Response;

public class InfoResponse extends Response{

	private static final long serialVersionUID = 2961656194306862829L;
	
	private String info = null;
	
	public InfoResponse( String info ) {
		super( MessageType.MT_INFO_RES, null, "ZZ TOP" );
		this.info = info;
	}
	
	public String getInfo() {
		return info;
	}
}
