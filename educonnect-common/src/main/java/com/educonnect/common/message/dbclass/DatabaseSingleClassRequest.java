package com.educonnect.common.message.dbclass;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Request;

public class DatabaseSingleClassRequest extends Request {

	private static final long serialVersionUID = 6543730851840609906L;

	private int clazz = -1;
	private char section = 'z';
	
	public DatabaseSingleClassRequest( int clazz, char section ) {
		super( MessageType.MT_DB_SINGLE_CLASS_REQ );
		this.clazz = clazz;
		this.section = section;
	}
	
	public int getClazz() {
		return clazz;
	}
	
	public char getSection() {
		return section;
	}
}
