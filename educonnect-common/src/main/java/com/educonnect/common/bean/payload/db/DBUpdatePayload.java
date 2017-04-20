package com.educonnect.common.bean.payload.db;

import com.educonnect.common.bean.db.Row;
import com.educonnect.common.bean.payload.Payload;

public class DBUpdatePayload extends Payload{
	
	private Row[] rows = null;

	public DBUpdatePayload( Row[] rows ) {
		this.rows = rows;
	}
	
	public Row[] getRows() {
		return rows;
	}
}
