package com.educonnect.common.message.dbupdate;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Request;

public class RowUpdateRequest extends Request {

	private static final long serialVersionUID = 4324681963835497306L;

	private Row[] rows = null;
	
	public RowUpdateRequest( Row[] rows ) {
		super( MessageType.MT_DB_ROW_UPDATE_REQ );
		this.rows = rows;
	}
	
	public Row[] getRows() {
		return rows;
	}
}
