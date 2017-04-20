package com.educonnect.common.bean.payload.db;

import com.educonnect.common.bean.db.DBFeedbackToken;
import com.educonnect.common.bean.payload.Payload;

public class DBFeedbackPayload extends Payload{

	private DBFeedbackToken[] tokens = null;
	
	public DBFeedbackPayload( DBFeedbackToken[] tokens ) {
		this.tokens = tokens;
	}
	
	public DBFeedbackToken[] getTokens() {
		return tokens;
	}
}
