package com.educonnect.common.message.core;

import com.educonnect.common.message.MessageType;

public abstract class Request extends Message {

	private static final long serialVersionUID = -4375400437137475459L;

	protected Request( MessageType msgType ) {
		super( msgType ) ;
	}
}
