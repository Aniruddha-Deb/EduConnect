package com.educonnect.common.bean.db;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.db.DBUpdatePayload;

public class DBUpdateBean extends Bean{

	public DBUpdateBean( Row[] rows ) {
		super( Header.DBUPDATE, new DBUpdatePayload( rows ) );
	}
}
