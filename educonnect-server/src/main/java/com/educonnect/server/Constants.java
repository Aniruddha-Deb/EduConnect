package com.educonnect.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Constants {

	private static Logger log = null;
	
	public static final String DIR_PATH  =  new File( Constants.class.getProtectionDomain().getCodeSource().getLocation().getPath() ).getParent() + File.separator + "res";
	
	public static final String KEYSTORE_LOC = DIR_PATH + File.separator + 
												"server.keystore";
	
	public static final String KEYSTORE_PASSWD   = "nlcy9408";
	
	public static final int SERVER_PORT = 1132;
	
	static {
		loadLoggerProperties();
		log.info( "Loaded properties for logger" );
		checkForKeystore();
		log.info( "Successfully retrieved and loaded keyStore" );
	}
	
	private static void loadLoggerProperties() {
	    Properties props = new Properties(); 
	    try { 
	        InputStream configStream = Constants.class.getResourceAsStream( "/log4j.properties"); 
	        props.load( configStream ); 
	        configStream.close(); 
	    } catch ( IOException e ) {
	    	e.printStackTrace();
	    } 
	    props.setProperty( "log4j.appender.file.File", DIR_PATH + File.separator + "server.log" ); 
	    LogManager.resetConfiguration(); 
	    PropertyConfigurator.configure( props );
	    
	    log = Logger.getLogger( Constants.class );
	}
	
	private static void checkForKeystore() {
		File f = new File( KEYSTORE_LOC );
		if( !f.exists() ) {
			log.fatal( "KeyStore not found in " + KEYSTORE_LOC, 
										new FileNotFoundException() );
			System.exit( -1 );
		}
	}
}
