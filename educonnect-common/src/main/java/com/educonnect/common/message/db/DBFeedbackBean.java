package com.educonnect.common.message.db;

import com.educonnect.common.message.Bean;
import com.educonnect.common.message.header.Header;
import com.educonnect.common.message.payload.db.DBFeedbackPayload;

public class DBFeedbackBean extends Bean{
	
	public DBFeedbackBean( DBFeedbackToken[] tokens ) {
		super( Header.DBFEEDBACK, new DBFeedbackPayload( tokens ) );
	}
}
