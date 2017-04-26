package com.educonnect.common.message.db;

import com.educonnect.common.message.Bean;
import com.educonnect.common.message.header.Header;
import com.educonnect.common.message.payload.db.DatabasePayload;

public class DatabaseBean extends Bean {
	
	public DatabaseBean( int clazz, char section, Student[] students ) {
		super( Header.DATABASE, new DatabasePayload( clazz, section, students ) );
	}
}
