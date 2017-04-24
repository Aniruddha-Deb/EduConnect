package com.educonnect.admin.ui;

import java.awt.Font;
import java.io.InputStream;

public class UIConstants {
	
	public static Font FONT = null;
	public static final String LOGIN_PANEL = "Login Panel";
	public static final String EDIT_PANEL  = "Edit Panel";
	
	
	static {
		String pathName = null;
		String sysName = System.getProperty( "os.name" ).toLowerCase();
		
		if( sysName.indexOf( "win" ) >= 0 ) {
			pathName = "win.ttf";
		}
		else if( sysName.indexOf( "mac" ) >= 0 ) {
			pathName = "mac.ttf";
		}
		else if( sysName.indexOf( "nix" ) >= 0 ) {
			pathName = "ubuntu.ttf";
		}

		if( pathName == null ) {
			FONT = new Font( "Helvetica", Font.PLAIN, 10 );
		}
		else {
			try {
				System.out.println( pathName );
				InputStream is = UIConstants.class.getResourceAsStream("/" + pathName ) ;
				FONT = Font.createFont( Font.TRUETYPE_FONT, is );
			} catch ( Exception e ) {
				e.printStackTrace();
			} 
		}
	}

}
