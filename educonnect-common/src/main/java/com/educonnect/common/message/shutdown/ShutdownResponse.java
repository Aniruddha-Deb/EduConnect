package com.educonnect.common.message.shutdown;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.core.Response;

public class ShutdownResponse extends Response {

	private static final long serialVersionUID = 3645132439807642741L;

	public ShutdownResponse( String requestUID ) {
		super( MessageType.MT_SHUTDOWN_RES, ResponseStatus.PROCESS_OK, requestUID );
	}
}
