package com.educonnect.common.message.dbclass;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Request;

public class DatabaseAllClassesRequest extends Request{

	private static final long serialVersionUID = -494574476243352234L;

	public DatabaseAllClassesRequest() {
		super( MessageType.MT_DB_ALL_CLASSES_REQ );
	}
}
