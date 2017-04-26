package com.educonnect.common.message;

import com.educonnect.common.message.header.Header;
import com.educonnect.common.message.payload.Payload;

/**
 * A bean is the smallest unit of data sent through the connection according to 
 * the EduConnect protocol. It consists of a {@link Header} and a {@link Payload}. 
 * The header tells the receiver about the contents of the payload whereas the 
 * payload contains the content that the sender wants to send </p>
 * 
 * @author Sensei
 */
public class Bean {
	
	/**
	 * The header of the bean
	 */
	private Header  header = null;
	/**
	 * The payload carried by the bean
	 */
	private Payload payload = null;

	/**
	 * The standard constructor for creating a bean object.
	 * 
	 * @param header The header of the bean - Must be a literal of the enum 
	 * {@link Header}
	 * @param payload The payload to be carried by the bean - Must be a subclass 
	 * or an instantiation of {@link Payload}.
	 */
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
