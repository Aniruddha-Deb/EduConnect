package com.educonnect.server;

import org.apache.log4j.Logger;

import com.educonnect.server.engine.ServerEngine;

public class EduConnectServer {
	
	private static final Logger log = Logger.getLogger( EduConnectServer.class );
	
	public static void main( String[] args ) {
		log.info( "Starting the Server" );
		new ServerEngine().start();
		log.info( "Closing the Server" );
	}

}
