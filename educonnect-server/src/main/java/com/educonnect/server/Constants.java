package com.educonnect.server;

import java.io.File;
import java.io.FileNotFoundException;

public class Constants {

	public static final String DIR_PATH  =  new File( Constants.class.getProtectionDomain().getCodeSource().getLocation().getPath() ).getParent() + File.separator + "res";
	
	public static final String KEYSTORE_LOC = DIR_PATH + File.separator + 
												"server.keystore";
	
	public static final String KEYSTORE_PASSWD   = "nlcy9408";
	
	public static final int SERVER_PORT = 1132;
	
	static {
		File f = new File( KEYSTORE_LOC );
		if( !f.exists() ) {
			try {
				throw new FileNotFoundException( 
					"Server keystore not found in directory " + KEYSTORE_LOC );
			} catch ( FileNotFoundException e ) {
				e.printStackTrace();
			}
			System.exit( -1 );
		}
	}
}
