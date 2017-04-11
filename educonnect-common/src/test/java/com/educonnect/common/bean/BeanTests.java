package com.educonnect.common.bean;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.educonnect.common.bean.header.Header;
import com.educonnect.common.bean.payload.AuthPayload;
import com.educonnect.common.bean.payload.FilePayload;
import com.educonnect.common.bean.payload.LoginPayload;
import com.educonnect.common.bean.payload.TextPayload;

public class BeanTests {

	private static final Logger log = LogManager.getLogger( BeanTests.class );
	
	private static final int  CLASS   = 9;
	private static final char SECTION = 'D';
	private static final int  ROLLNO  = 11;
	
	private static final String EMAIL_ID = "aniruddha.deb.2002@gmail.com";
	private static final String PASSWORD = "notrealpasswd";
	
	private static final String SENDER = "Aniruddha Deb";
	private static final String TEXT   = "Test!";
	
	private static final String IN_FILE_PATH  = "src/test/resources/filetest.png";
	private static final String OUT_FILE_PATH = "src/test/resources/out.png";
	
	@Test
	public void loginBeanTest() {
		LoginBean loginBean = new LoginBean( CLASS, SECTION, ROLLNO );
		log.debug( "Created login bean" );
		Header loginHeader = loginBean.getHeader();
		assertEquals( loginHeader, Header.LOGIN );
		
		assertThat( loginBean.getPayload(), instanceOf( LoginPayload.class ) );
		LoginPayload loginPayload = (LoginPayload)loginBean.getPayload();
		
		assertEquals( loginPayload.getGrade(), CLASS );
		assertEquals( loginPayload.getSection(), SECTION );
		assertEquals( loginPayload.getRollNo(), ROLLNO );
		log.debug( "Login bean test passed" );
	}
	
	@Test
	public void authBeanTest() {
		AuthBean authBean = new AuthBean( EMAIL_ID, PASSWORD );
		log.debug( "Created authentication bean" );
		Header authHeader = authBean.getHeader();
		assertEquals( authHeader, Header.AUTH );
		
		assertThat( authBean.getPayload(), instanceOf( AuthPayload.class ) );
		AuthPayload authPayload = (AuthPayload)authBean.getPayload();
		
		assertEquals( authPayload.getEmailId(), EMAIL_ID );
		assertEquals( authPayload.getPasswd(), PASSWORD );
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
}
