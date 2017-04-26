package com.educonnect.common.message.shutdown;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Request;

public class ShutdownRequest extends Request {

	private static final long serialVersionUID = 1727354626757052246L;

	public ShutdownRequest() {
		super( MessageType.MT_SHUTDOWN_REQ );
	}
}
