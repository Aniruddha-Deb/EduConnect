package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.DatabasePayload;

public class DatabaseBean extends Bean {
	
	public DatabaseBean( String[] headers, String[][] data ) {
		super( Header.DATABASE, new DatabasePayload( headers, data ) );
	}
}
