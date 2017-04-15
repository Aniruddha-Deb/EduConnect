package com.educonnect.common.parser;

import static com.educonnect.common.bean.BeanTests.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.educonnect.common.bean.*;
import com.educonnect.common.bean.payload.*;
import com.educonnect.common.client.ClientType;
import com.educonnect.common.serializer.Serializer;

public class ParserTest {

	@Test
	public void loginParserTest() {
		LoginBean bean = new LoginBean( EMAIL_ID, PASSWORD, ClientType.STUDENT );
		String serializedBean = Serializer.serialize( bean );
		LoginPayload parsedPayload = (LoginPayload)Parser.parse( serializedBean );
		LoginPayload beanPayload = (LoginPayload)bean.getPayload();
		
		assertEquals( parsedPayload, beanPayload );
	}
	
	@Test
	public void authParserTest() {
		AuthBean bean = new AuthBean( AUTH_TOKEN );
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

	@Test
	public void failParserTest() {
		FailBean bean = new FailBean( FAIL_CAUSE );
		String serializedBean = Serializer.serialize( bean );
		FailPayload parsedPayload = (FailPayload)Parser.parse( serializedBean );
		FailPayload beanPayload = (FailPayload)bean.getPayload();
		
		assertEquals( parsedPayload, beanPayload );
	}
	
	@Test
	public void infoParserTest() {
		InfoBean bean = new InfoBean( INFO );
		String serializedBean = Serializer.serialize( bean );
		InfoPayload parsedPayload = (InfoPayload)Parser.parse( serializedBean );
		InfoPayload beanPayload = (InfoPayload)bean.getPayload();
		
		assertEquals( parsedPayload, beanPayload );
	}

	@Test
	public void registerParserTest() {
		RegisterBean bean = new RegisterBean( CLASS, SECTION, ROLLNO, EMAIL_ID, PASSWORD );
		String serializedBean = Serializer.serialize( bean );
		RegisterPayload parsedPayload = (RegisterPayload)Parser.parse( serializedBean );
		RegisterPayload beanPayload = (RegisterPayload)bean.getPayload();
		
		assertEquals( parsedPayload, beanPayload );
	}

	@Test
	public void shutdownParserTest() {
		ShutdownBean bean = new ShutdownBean();
		String serializedBean = Serializer.serialize( bean );
		ShutdownPayload parsedPayload = (ShutdownPayload)Parser.parse( serializedBean );
		ShutdownPayload beanPayload = (ShutdownPayload)bean.getPayload();
		
		assertEquals( parsedPayload, beanPayload );
	}
}
