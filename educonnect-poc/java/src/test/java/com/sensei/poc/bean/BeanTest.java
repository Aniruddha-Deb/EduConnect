package com.sensei.poc.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sensei.poc.bean.payload.LoginJSON;
import com.sensei.poc.bean.payload.TextJSON;
import com.sensei.poc.json.Parser;

public class BeanTest {

	public static final Logger log = LogManager.getLogger( BeanTest.class );
	
	public static void main( String[] args ) {
		
		LoginBean loginBean = new LoginBean( new LoginJSON( 9, 'D', 11 ) );
		log.debug( "Login Bean made" );
		log.debug( loginBean.toString() );
		
		TextBean textBean = new TextBean( new TextJSON( "Aniruddha Deb", "Hiiii!" ) );
		log.debug( "Text bean made" );
		log.debug( textBean.toString() );
		
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

}
