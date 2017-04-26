package com.educonnect.common.message.core;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.ResponseStatus;

public abstract class Response extends Message {

	private static final long serialVersionUID = 8722559733406840413L;
	
	private String correlationId = null ;
	private ResponseStatus status = null ;
	private String statusText = null ;

	public Response( MessageType msgType, ResponseStatus status, String requestUID ) {
		super( msgType ) ;
		this.correlationId = requestUID ;
		this.status = status ;
	}
	
	public void setStatusText( String txt ) {
		this.statusText = txt ;
	}
	
	public String getStatusText() {
		if( this.statusText == null ) {
			return this.status.name() ;
		}
		return this.statusText ;
	}
	
	public String getCorrelationId() {
		return this.correlationId ;
	}
	
	public ResponseStatus getResponseStatus() {
		return this.status ;
	}
}
