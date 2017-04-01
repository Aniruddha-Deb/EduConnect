package com.educonnect.common.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * A bean is a serializable object which is the smallest unit of data sent between 
 * the client and server through a particular protocol. The bean does not care 
 * about the protocol through which it is sent and received as the receiver and 
 * sender both share the same protocol through the bean - The EduConnect Protocol. 
 * 
 * A bean contains a header and a payload. The header is an element of the Header 
 * enum which specifies the type of payload the bean is carrying - be it a text 
 * message, info message or an image. It is the task of the receiver to convert 
 * the payload to whichever object type is specified through the header. 
 * 
 * The class also completely abstracts the fact that the payload is a byte[]. This 
 * reduces overhead and also allows the programmer to directly pass objects to 
 * the bean. The bean has it's own convenience <code>serialize</code> and 
 * <code>deserialize</code> methods to efficiently handle the serialization 
 * and deserialization of objects.
 * 
 * @author Sensei
 *
 */
public class Bean implements Serializable{
	
	private static final long serialVersionUID = 8864022887689600887L;

	/** 
	 * This enum contains the various header types for the header of the bean. 
	 * Using an enum prevents consistency errors across the client-server network. 
	 * 
	 * @author Sensei
	 *
	 */
	public enum Header {
		OK, INFO, TEXT, SUCCESS, FAILURE, LOGIN, IMAGE;
	}
	
	private Header header  = null;
	private byte[] payload = null;
	
	public Bean( Header header, Object payload ) {
		this.header = header;
		this.payload = serialize( payload );
	}
	
	public Header getHeader() {
		return header;
	}
	
	public Object getPayload() {
		return deserialize( payload );
	}
	
	/**
	 * Convenience method for the serialization of an object into a byte[]. Note 
	 * that the object must implement serializable!
	 * 
	 * @param payload The object to be serialized
	 * @return a byte[] containing the serialized object
	 */
	private byte[] serialize( Object payload ) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		
		try {
			baos = new ByteArrayOutputStream( 2048 );
			oos = new ObjectOutputStream( baos );
			oos.writeObject( payload );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		
		return baos.toByteArray();
	}

	/**
	 * Convenience method for the deserialization of a byte[] into an object
	 * 
	 * @param payload The byte[] to be serialized
	 * @return an object, which is the result of deserializing the byte[]
	 */
	private Object deserialize( byte[] payload ) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		Object o = null;
		
		try {
			bais = new ByteArrayInputStream( payload );
			ois = new ObjectInputStream( bais );
			o = ois.readObject();
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		
		return o;
	}
	
}
