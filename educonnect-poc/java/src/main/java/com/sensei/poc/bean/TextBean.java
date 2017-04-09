package com.sensei.poc.bean;

import com.sensei.poc.bean.header.BeanHeader;
import com.sensei.poc.bean.payload.TextJSON;

public class TextBean extends Bean{
	
	public TextBean( TextJSON payload ) {
		super( BeanHeader.TEXT, payload );
	}
}
