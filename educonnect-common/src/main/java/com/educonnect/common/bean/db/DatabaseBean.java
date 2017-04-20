package com.educonnect.common.bean.db;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.db.DatabasePayload;

public class DatabaseBean extends Bean {
	
	public DatabaseBean( int clazz, char section, Student[] students ) {
		super( Header.DATABASE, new DatabasePayload( clazz, section, students ) );
	}
}
