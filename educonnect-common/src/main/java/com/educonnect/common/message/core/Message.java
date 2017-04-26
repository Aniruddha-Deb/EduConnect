package com.educonnect.common.message.core;

import java.io.Serializable;

import com.educonnect.common.message.MessageType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class Message implements Serializable {

	private static final long serialVersionUID = 1232280938084133522L ;
	
	private static Gson GSON = new GsonBuilder().setPrettyPrinting()
			                                    .create() ;

	private transient MessageType messageType = null ;
	private String uid = null ;
	
	protected Message( MessageType msgType ) {
		if( msgType == null ) {
			throw new IllegalArgumentException( "Message type can't be null." ) ;
		}
		this.messageType = msgType ;
		uid = java.util.UUID.randomUUID().toString() ;
	}
	
	public String getMessageHeaderAsJSON() {
		JsonObject jsonObj = new JsonObject() ;
		JsonPrimitive msgType = new JsonPrimitive( this.messageType.name() ) ;
		jsonObj.add( "msgType", msgType) ;
		return GSON.toJson(jsonObj) ;
	}
	
	public String getMessageBodyAsJSON() {
		return GSON.toJson(this) ;
	}
	
	public MessageType getMessageType() {
		return this.messageType ;
	}
	
	public String getUID() {
		return this.uid ;
	}
}
