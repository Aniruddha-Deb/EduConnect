package com.sensei.poc.bean;

import com.sensei.poc.bean.header.BeanHeader;
import com.sensei.poc.bean.payload.TextJSON;

public class TextBean extends Bean{
	
	public TextBean( String sender, String text ) {
		super( BeanHeader.TEXT, new TextJSON( sender, text ) );
	}
}
