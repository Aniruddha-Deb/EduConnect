package com.educonnect.common.message.core;

import java.io.Serializable;

import com.educonnect.common.message.MessageType;

public abstract class Message implements Serializable {

	private static final long serialVersionUID = 1232280938084133522L ;
	
	private transient MessageType messageType = null ;
	private String uid = null ;
	
	protected Message( MessageType msgType ) {
		if( msgType == null ) {
			throw new IllegalArgumentException( "Message type can't be null." ) ;
		}
		this.messageType = msgType ;
		uid = java.util.UUID.randomUUID().toString() ;
	}
	
	public MessageType getMessageType() {
		return this.messageType ;
	}
	
	public String getUID() {
		return this.uid ;
	}
}
