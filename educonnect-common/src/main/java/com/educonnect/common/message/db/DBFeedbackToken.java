package com.educonnect.common.message.db;

public class DBFeedbackToken {
	
	private int tempUID = -1;
	private int DBUID   = -1;
	
	public DBFeedbackToken( int tempUID, int DBUID ) {
		this.tempUID = tempUID;
		this.DBUID   = DBUID;
	}

	public int getTempUID() {
		return tempUID;
	}
	
	public int getDBUID() {
		return DBUID;
	}
}
