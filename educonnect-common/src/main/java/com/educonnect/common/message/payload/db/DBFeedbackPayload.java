package com.educonnect.common.message.payload.db;

import com.educonnect.common.message.db.DBFeedbackToken;
import com.educonnect.common.message.payload.Payload;

public class DBFeedbackPayload extends Payload{

	private DBFeedbackToken[] tokens = null;
	
	public DBFeedbackPayload( DBFeedbackToken[] tokens ) {
		this.tokens = tokens;
	}
	
	public DBFeedbackToken[] getTokens() {
		return tokens;
	}
}
