package com.educonnect.admin.ui;

import java.awt.Font;
import java.io.File;

public class UIConstants {
	
	public static Font FONT = null;
	public static final String LOGIN_PANEL = "Login Panel";
	public static final String EDIT_PANEL  = "Edit Panel";
	
	
	static {
		String pathName = null;
		String sysName = System.getProperty( "os.name" ).toLowerCase();
		
		if( sysName.indexOf( "win" ) >= 0 ) {
			pathName = "src/main/resources/fonts/win.ttf";
		}
		else if( sysName.indexOf( "mac" ) >= 0 ) {
			pathName = "src/main/resources/fonts/mac.ttf";
		}
		else if( sysName.indexOf( "nix" ) >= 0 ) {
			pathName = "src/main/resources/fonts/ubuntu.ttf";
		}

		if( pathName == null ) {
			FONT = new Font( "Helvetica", Font.PLAIN, 10 );
		}
		else {
			try {
				FONT = Font.createFont( Font.TRUETYPE_FONT, new File( pathName ) );
			} catch ( Exception e ) {
				e.printStackTrace();
			} 
		}
	}

}
