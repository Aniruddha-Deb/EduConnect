package com.educonnect.common.message.payload.db;

import com.educonnect.common.message.db.Row;
import com.educonnect.common.message.payload.Payload;

public class DBUpdatePayload extends Payload{
	
	private Row[] rows = null;

	public DBUpdatePayload( Row[] rows ) {
		this.rows = rows;
	}
	
	public Row[] getRows() {
		return rows;
	}
}
