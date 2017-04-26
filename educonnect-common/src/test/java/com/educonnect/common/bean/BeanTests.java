package com.educonnect.common.bean;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.AuthBean;
import com.educonnect.common.message.FailBean;
import com.educonnect.common.message.FileBean;
import com.educonnect.common.message.InfoBean;
import com.educonnect.common.message.LoginBean;
import com.educonnect.common.message.RegisterBean;
import com.educonnect.common.message.ShutdownBean;
import com.educonnect.common.message.TextBean;
import com.educonnect.common.message.header.Header;
import com.educonnect.common.message.payload.AuthPayload;
import com.educonnect.common.message.payload.FailPayload;
import com.educonnect.common.message.payload.FilePayload;
import com.educonnect.common.message.payload.InfoPayload;
import com.educonnect.common.message.payload.LoginPayload;
import com.educonnect.common.message.payload.RegisterPayload;
import com.educonnect.common.message.payload.ShutdownPayload;
import com.educonnect.common.message.payload.TextPayload;

public class BeanTests {

	private static final Logger log = LogManager.getLogger( BeanTests.class );
	
	public static final int  CLASS   = 9;
	public static final char SECTION = 'D';
	public static final int  ROLLNO  = 11;
	
	public static final String EMAIL_ID = "aniruddha.deb.2002@gmail.com";
	public static final String PASSWORD = "notrealpasswd";
	
	public static final long AUTH_TOKEN = 11285234L;
	
	public static final String SENDER = "Aniruddha Deb";
	public static final String TEXT   = "Test!";
	
	public static final String IN_FILE_PATH  = "src/test/resources/filetest.png";
	public static final String OUT_FILE_PATH = "src/test/resources/out.png";
	
	public static final String FAIL_CAUSE = "Just cause :P";
	public static final String INFO       = "Whoever's reading this is stupid!";
	
	@Test
	public void loginBeanTest() {
		LoginBean loginBean = new LoginBean( EMAIL_ID, PASSWORD.toCharArray(), ClientType.ADMIN );
		log.debug( "Created login bean" );
		Header loginHeader = loginBean.getHeader();
		assertEquals( loginHeader, Header.LOGIN );
		
		assertThat( loginBean.getPayload(), instanceOf( LoginPayload.class ) );
		LoginPayload loginPayload = (LoginPayload)loginBean.getPayload();
		
		assertEquals( loginPayload.getEmailId(), EMAIL_ID );
		assertThat( Arrays.equals( loginPayload.getPassword(), PASSWORD.toCharArray() ), equalTo( true ) );
		assertEquals( loginPayload.getClientType(), ClientType.ADMIN );
		log.debug( "Login bean test passed" );
	}
	
	@Test
	public void authBeanTest() {
		AuthBean authBean = new AuthBean( AUTH_TOKEN );
		log.debug( "Created authentication bean" );
		Header authHeader = authBean.getHeader();
		assertEquals( authHeader, Header.AUTH );
		
		assertThat( authBean.getPayload(), instanceOf( AuthPayload.class ) );
		AuthPayload authPayload = (AuthPayload)authBean.getPayload();
		
		assertEquals( authPayload.getAuthCode(), AUTH_TOKEN );
		log.debug( "Authentication bean test passed" );
	}
	
	@Test
	public void textBeanTest() {
		TextBean textBean = new TextBean( SENDER, TEXT );
		log.debug( "Created text bean" );
		Header textHeader = textBean.getHeader();
		assertEquals( textHeader, Header.TEXT );
		
		assertThat( textBean.getPayload(), instanceOf( TextPayload.class ) );
		TextPayload textPayload = (TextPayload)textBean.getPayload();
		
		assertEquals( textPayload.getSender(), SENDER );
		assertEquals( textPayload.getText(), TEXT );
		log.debug( "Text bean test passed" );
	}
	
	@Test
	public void fileBeanTest() {
		FileBean fileBean = new FileBean( IN_FILE_PATH );
		log.debug( "Created file bean" );
		Header fileHeader = fileBean.getHeader();
		assertEquals( fileHeader, Header.FILE );
		
		assertThat( fileBean.getPayload(), instanceOf( FilePayload.class ) );
		FilePayload loginPayload = (FilePayload)fileBean.getPayload();

		File inFile = new File( IN_FILE_PATH );
		File outFile = new File( OUT_FILE_PATH );
		assertEquals( outFile.delete(), true );
		
		loginPayload.unloadFile( OUT_FILE_PATH );
		try {
			assertTrue( FileUtils.contentEquals( inFile, outFile ) );
 		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		log.debug( "File bean test passed" );
	}
	
	@Test
	public void failBeanTest() {
		FailBean failBean = new FailBean( FAIL_CAUSE );
		log.debug( "Created fail bean" );
		Header failHeader = failBean.getHeader();
		assertEquals( failHeader, Header.FAIL );
		
		assertThat( failBean.getPayload(), instanceOf( FailPayload.class ) );
		FailPayload failPayload = (FailPayload)failBean.getPayload();
		
		assertEquals( failPayload.getCause(), FAIL_CAUSE );
		log.debug( "Fail bean test passed" );
	}
	
	@Test
	public void infoBeanTest() {
		InfoBean infoBean = new InfoBean( INFO );
		log.debug( "Created info bean" );
		Header infoHeader = infoBean.getHeader();
		assertEquals( infoHeader, Header.INFO );
		
		assertThat( infoBean.getPayload(), instanceOf( InfoPayload.class ) );
		InfoPayload infoPayload = (InfoPayload)infoBean.getPayload();
		
		assertEquals( infoPayload.getInfo(), INFO );
		log.debug( "Info bean test passed" );
	}
	
	@Test
	public void registerBeanTest() {
		RegisterBean registerBean = new RegisterBean( CLASS, SECTION, ROLLNO, EMAIL_ID, PASSWORD );
		log.debug( "Created register bean" );
		Header registerHeader = registerBean.getHeader();
		assertEquals( registerHeader, Header.REGISTER );
		
		assertThat( registerBean.getPayload(), instanceOf( RegisterPayload.class ) );
		RegisterPayload registerPayload = (RegisterPayload)registerBean.getPayload();
		
		assertEquals( registerPayload.getClazz()  , CLASS    );
		assertEquals( registerPayload.getSection(), SECTION  );
		assertEquals( registerPayload.getRollNo() , ROLLNO   );
		assertEquals( registerPayload.getEmailId(), EMAIL_ID );
		assertEquals( registerPayload.getPasswd() , PASSWORD );
		log.debug( "Authentication bean test passed" );
	}
	
	@Test
	public void shutdownBeanTest() {
		ShutdownBean shutdownBean = new ShutdownBean();
		log.debug( "Created shutdown bean" );
		Header shutdownHeader = shutdownBean.getHeader();
		assertEquals( shutdownHeader, Header.SHUTDOWN );
		
		assertThat( shutdownBean.getPayload(), instanceOf( ShutdownPayload.class ) );
		
		log.debug( "Info bean test passed" );
	}
}
