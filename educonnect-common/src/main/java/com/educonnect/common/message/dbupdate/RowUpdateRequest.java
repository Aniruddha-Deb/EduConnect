package com.educonnect.common.message.dbupdate;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Request;

public class RowUpdateRequest extends Request {

	private static final long serialVersionUID = 4324681963835497306L;

	private ClassOfRows[] classOfRows = null;
	
	public RowUpdateRequest( ClassOfRows[] classOfrows ) {
		super( MessageType.MT_DB_ROW_UPDATE_REQ );
		this.classOfRows = classOfrows;
	}

	public ClassOfRows[] getClassOfRows() {
		return classOfRows;
	}
}
