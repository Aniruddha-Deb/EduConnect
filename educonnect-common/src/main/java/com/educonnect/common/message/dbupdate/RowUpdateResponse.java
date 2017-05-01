package com.educonnect.common.message.dbupdate;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.core.Response;

public class RowUpdateResponse extends Response{

	private static final long serialVersionUID = -9055334015948875556L;

	private boolean successful = false;
	
	public RowUpdateResponse( ResponseStatus status, String requestUID, 
							  boolean wasSuccessful ) {
		super( MessageType.MT_DB_ROW_UPDATE_RES, status, requestUID );
		this.successful = wasSuccessful;
	}
	
	public boolean isSuccessful() {
		return successful;
	}
}
