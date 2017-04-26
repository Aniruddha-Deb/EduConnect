package com.educonnect.common.message.db;

import com.educonnect.common.message.Bean;
import com.educonnect.common.message.header.Header;
import com.educonnect.common.message.payload.db.DBUpdatePayload;

public class DBUpdateBean extends Bean{

	public DBUpdateBean( Row[] rows ) {
		super( Header.DBUPDATE, new DBUpdatePayload( rows ) );
	}
}
