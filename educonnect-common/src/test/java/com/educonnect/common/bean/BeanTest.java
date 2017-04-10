package com.educonnect.common.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.educonnect.common.bean.payload.AuthPayload;
import com.educonnect.common.bean.payload.FilePayload;
import com.educonnect.common.bean.payload.LoginPayload;
import com.educonnect.common.bean.payload.TextPayload;

public class BeanTest {

	private static final Logger log = LogManager.getLogger( BeanTest.class );
	
	public static void main( String[] args ) throws IOException {
		
		LoginBean loginBean = new LoginBean( 9, 'D', 11 );
		log.debug( "Made a loginBean" );
		LoginPayload loginBeanPayload = (LoginPayload)loginBean.getPayload();
		log.debug( "Class   = " + loginBeanPayload.getGrade() );
		log.debug( "Section = " + loginBeanPayload.getSection() );
		log.debug( "Roll no = " + loginBeanPayload.getRollNo() );
		
		AuthBean authBean = new AuthBean( "aniruddha.deb.2002@gmail.com", "notrealpasswd" );
		log.debug( "Made an authBean" );
		AuthPayload authBeanPayload = (AuthPayload)authBean.getPayload();
		log.debug( "eMail id = " + authBeanPayload.getEmailId() );
		log.debug( "password = " + authBeanPayload.getPasswd()  );

		TextBean textBean = new TextBean( "Aniruddha Deb", "First EduConnect text" );
		log.debug( "Made a textBean" );
		TextPayload textBeanPayload = (TextPayload)textBean.getPayload();
		log.debug( "Sender = " + textBeanPayload.getSender() );
		log.debug( "Text   = " + textBeanPayload.getText()   );
		
		FileBean fileBean = new FileBean( "/Users/Sensei/test.png" );
		log.debug( "Made a fileBean" );
		FilePayload fileBeanPayload = (FilePayload)fileBean.getPayload();
		
		FileOutputStream fos = new FileOutputStream( new File( "/Users/Sensei/out.png" ) );
		fos.write( Files.readAllBytes( Paths.get( fileBeanPayload.getFile().getAbsolutePath() ) ) );
		fos.close();
		log.debug( "Wrote file to out.png" );
	}
}
