package com.educonnect.common.bean.db;

import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.db.DBFeedbackPayload;

public class DBFeedbackBean extends Bean{
	
	public DBFeedbackBean( DBFeedbackToken[] tokens ) {
		super( Header.DBFEEDBACK, new DBFeedbackPayload( tokens ) );
	}
}
