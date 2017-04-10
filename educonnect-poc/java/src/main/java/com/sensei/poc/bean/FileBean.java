package com.sensei.poc.bean;

import com.sensei.poc.bean.header.BeanHeader;
import com.sensei.poc.bean.payload.FileJSON;

public class FileBean extends Bean {

	public FileBean( String filePath ) {
		super( BeanHeader.FILE, new FileJSON( filePath ) );
	}

}
