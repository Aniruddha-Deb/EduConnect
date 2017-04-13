package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.InfoPayload;

public class InfoBean extends Bean {

	public InfoBean( String info ) {
		super( Header.INFO, new InfoPayload( info ) );
	}
}
