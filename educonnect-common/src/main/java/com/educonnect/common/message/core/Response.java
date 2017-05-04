package com.educonnect.common.message.core;

import com.educonnect.common.message.MessageType;

public abstract class Response extends Message {

	private static final long serialVersionUID = 8722559733406840413L;
	
	private String correlationId = null ;
	private String statusText = null ;

	public Response( MessageType msgType, String requestUID ) {
		super( msgType ) ;
		if( requestUID == null ) {
			throw new IllegalArgumentException( "requestUID cannot be null" );
		}
		this.correlationId = requestUID ;
	}
	
	public void setStatusText( String txt ) {
		this.statusText = txt ;
	}
	
	public String getStatusText() {
		return this.statusText ;
	}
	
	public String getCorrelationId() {
		return this.correlationId ;
	}	
}
