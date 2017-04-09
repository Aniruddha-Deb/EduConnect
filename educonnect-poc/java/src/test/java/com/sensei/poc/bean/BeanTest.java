package com.sensei.poc.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sensei.poc.bean.payload.LoginJSON;
import com.sensei.poc.bean.payload.TextJSON;

public class BeanTest {

	public static final Logger log = LogManager.getLogger( BeanTest.class );
	
	public static void main( String[] args ) {
		
		LoginBean loginBean = new LoginBean( new LoginJSON( 9, 'D', 11 ) );
		log.debug( "Login Bean made" );
		log.debug( loginBean.toString() );
		
		TextBean textBean = new TextBean( new TextJSON( "Aniruddha Deb", "Hiiii!" ) );
		log.debug( "Text bean made" );
		log.debug( textBean.toString() );
	}

}
