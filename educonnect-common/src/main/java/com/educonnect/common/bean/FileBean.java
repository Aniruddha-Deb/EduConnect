package com.educonnect.common.bean;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.FilePayload;

public class FileBean extends Bean {
	
	public FileBean( String filepath ) {
		super( Header.FILE, new FilePayload( filepath ) );
	}
}
