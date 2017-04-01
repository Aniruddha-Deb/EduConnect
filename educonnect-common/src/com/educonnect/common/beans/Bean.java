package com.educonnect.common.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Bean implements Serializable{
	
	private static final long serialVersionUID = 8864022887689600887L;

	public enum BeanConstants {
		OK, INFO, TEXT, SUCCESS, FAILURE, LOGIN, IMAGE;
	}
	
	private BeanConstants header  = null;
	private byte[]        payload = null;
	
	public Bean( BeanConstants header, Object payload ) {
		this.header = header;
		this.payload = serialize( payload );
	}
	
	public BeanConstants getHeader() {
		return header;
	}
	
	public Object getPayload() {
		return deserialize( payload );
	}
	
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
