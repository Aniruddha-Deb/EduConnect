package com.educonnect.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Constants {

	private static final Logger log = Logger.getLogger( Constants.class );
	
	public static final String DIR_PATH  =  new File( Constants.class.getProtectionDomain().getCodeSource().getLocation().getPath() ).getParent() + File.separator + "res";
	
	public static final String KEYSTORE_LOC = DIR_PATH + File.separator + 
												"server.keystore";
	
	public static final String KEYSTORE_PASSWD   = "nlcy9408";
	
	public static final int SERVER_PORT = 1132;
	
	private static Properties log4jProperties = null;
	
	private static String[] properties = {
			"log4j.rootLogger=DEBUG, stdout, file",
			"log4j.appender.stdout=org.apache.log4j.ConsoleAppender",
			"log4j.appender.stdout.Target=System.out",
			"log4j.appender.stdout.layout=org.apache.log4j.PatternLayout",
			"log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1} - %m%n",
			"log4j.appender.file=org.apache.log4j.RollingFileAppender",
			"log4j.appender.file.File=" + DIR_PATH + File.separator + "server.log",
			"log4j.appender.file.MaxFileSize=5MB",
			"log4j.appender.file.MaxBackupIndex=10",
			"log4j.appender.file.layout=org.apache.log4j.PatternLayout",
			"log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n	"	
	};
	
	static {
		loadLoggerProperties();
		log.debug( "Loaded properties for logger" );
		checkForKeystore();
		log.debug( "Successfully retrieved and loaded keyStore" );
	}
	
	private static void loadLoggerProperties() {
		log4jProperties = new Properties();
		
		for( String property : properties ) {
			String[] parts = property.split( "=" );
			log4jProperties.setProperty( parts[0], parts[1] );
		}
		
		try {
			FileOutputStream fos = new FileOutputStream( new File( "src/main/resources/log4j.properties" ) );
			log4jProperties.store( fos, null );
		} catch ( IOException e1 ) {
			e1.printStackTrace();
		}
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
