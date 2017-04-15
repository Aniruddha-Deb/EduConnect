package com.educonnect.admin;

import java.io.File;

public class Constants {

	public static String userName = null;
	public static final String DIR_PATH  = System.getProperty( "user.home" ) + "/.educonnect/admin";
	public static final String XLSX_FILE_PATH = DIR_PATH + "/file.xlsx";
	
	static {
		File dir = new File( DIR_PATH );
		if( !dir.exists() ) {
			dir.mkdirs();
		}		
	}	
}
