package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.FailPayload;

public class FailBean extends Bean {
	
	public FailBean( String cause ) {
		super( Header.FAIL, new FailPayload( cause ) );
	}
}
