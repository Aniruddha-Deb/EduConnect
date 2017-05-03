package com.educonnect.admin;

import java.io.File;

import com.educonnect.admin.ui.util.UIUtils;

public class Constants {

	public static final String RES_DIR_PATH  = new File( Constants.class.getProtectionDomain().getCodeSource().getLocation().getPath() ).getParent() + File.separator + "res";
	
	public static final String TRUSTSTORE_LOC = RES_DIR_PATH + File.separator +
												"client.truststore";
	public static final String TRUSTSTORE_PASSWD   = "public";
	
	public static final String SERVER_IP_ADDRESS = "192.168.0.100";
	public static final int    SERVER_PORT       = 1132;
	
	public static String USER_NAME = null;

	static {
		File f = new File( TRUSTSTORE_LOC );
		if( !f.exists() ) {
			UIUtils.showError( null, "TrustStore is not located in intended location." );
			System.exit( -1 );
		}
	}
}
