package com.educonnect.common.parser;

import static com.educonnect.common.bean.BeanTests.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.educonnect.common.bean.*;
import com.educonnect.common.bean.payload.*;
import com.educonnect.common.serializer.Serializer;

public class ParserTest {

	@Test
	public void loginParserTest() {
		LoginBean bean = new LoginBean( CLASS, SECTION, ROLLNO );
		String serializedBean = Serializer.serialize( bean );
		LoginPayload parsedPayload = (LoginPayload)Parser.parse( serializedBean );
		LoginPayload beanPayload = (LoginPayload)bean.getPayload();
		
		assertEquals( parsedPayload, beanPayload );
	}
	
	@Test
	public void authParserTest() {
		AuthBean bean = new AuthBean( EMAIL_ID, PASSWORD );
		String serializedBean = Serializer.serialize( bean );
		AuthPayload parsedPayload = (AuthPayload)Parser.parse( serializedBean );
		AuthPayload beanPayload = (AuthPayload)bean.getPayload();
		
		assertEquals( parsedPayload, beanPayload );
	}

	@Test
	public void fileParserTest() {
		FileBean bean = new FileBean( IN_FILE_PATH );
		String serializedBean = Serializer.serialize( bean );
		FilePayload parsedPayload = (FilePayload)Parser.parse( serializedBean );
		FilePayload beanPayload = (FilePayload)bean.getPayload();
		
		assertEquals( parsedPayload, beanPayload );
	}

	@Test
	public void textParserTest() {
		TextBean bean = new TextBean( SENDER, EMAIL_ID );
		String serializedBean = Serializer.serialize( bean );
		TextPayload parsedPayload = (TextPayload)Parser.parse( serializedBean );
		TextPayload beanPayload = (TextPayload)bean.getPayload();
		
		assertEquals( parsedPayload, beanPayload );
	}

}
