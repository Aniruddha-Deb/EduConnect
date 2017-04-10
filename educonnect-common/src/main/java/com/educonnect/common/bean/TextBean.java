package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.TextPayload;

public class TextBean extends Bean {
	
	public TextBean( String sender, String text ) {
		super( Header.TEXT, new TextPayload( sender, text ) );
	}
}
