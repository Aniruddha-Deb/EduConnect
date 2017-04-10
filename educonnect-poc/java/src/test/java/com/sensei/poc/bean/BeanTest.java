package com.sensei.poc.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sensei.poc.bean.payload.FileJSON;
import com.sensei.poc.bean.payload.LoginJSON;
import com.sensei.poc.bean.payload.TextJSON;
import com.sensei.poc.json.Parser;

public class BeanTest {

	public static final Logger log = LogManager.getLogger( BeanTest.class );
	public static final String FILE_LOC = "/Users/Sensei/IMG_SOME_GIBBERISH.png"; 
	
	public static void main( String[] args ) {
		
		LoginBean loginBean = new LoginBean( 9, 'D', 11 );
		log.debug( "Login Bean made" );
		log.debug( loginBean.toString() );
		
		TextBean textBean = new TextBean( "Aniruddha Deb", "Hiiii!" );
		log.debug( "Text bean made" );
		log.debug( textBean.toString() );
		
		FileBean fileBean = new FileBean( "/Users/Sensei/temp/20170407_180903.jpg" );
		log.debug( "File bean made" );
		log.debug( fileBean.toString() );
		
		FileJSON parsedFileBean = (FileJSON)Parser.parse( fileBean.toString() );
		log.debug( "Parsed file bean" );
		writeFile( parsedFileBean );
		log.debug( "Wrote file to " + FILE_LOC );
		
		TextJSON parsedTextBean = (TextJSON)Parser.parse( textBean.toString() );
		log.debug( "Parsed text bean" );
		log.debug( "Sender: " + parsedTextBean.getSender() );
		log.debug( "Text: " + parsedTextBean.getText() );
		
		LoginJSON parsedLoginBean = (LoginJSON)Parser.parse( loginBean.toString() );
		log.debug( "Parsed login bean" );
		log.debug( "Grade    : " + parsedLoginBean.getGrade() );
		log.debug( "Roll no. : " + parsedLoginBean.getRollNo() );
		log.debug( "Section  : " + parsedLoginBean.getSection() );
	}

	private static void writeFile( FileJSON parsedFileBean ) {
		String base64FileString = parsedFileBean.getFile();
		byte[] file = Base64.getDecoder().decode( base64FileString );
		
		OutputStream os;
		try {
			os = Files.newOutputStream( Paths.get( FILE_LOC ) );
			os.write( file );
			os.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
