package com.educonnect.admin.ui;

import java.awt.Font;
import java.io.InputStream;

import javax.swing.UIManager;

import com.pagosoft.plaf.PgsLookAndFeel;

public class UIConstants {
	
	public static Font FONT = null;
	public static final String LOGIN_PANEL = "Login Panel";
	public static final String EDIT_PANEL  = "Edit Panel" ;
	
	private static final String UI_RES_ROOT   = "/com/educonnect/admin/ui/" ;

	private static final String ICON_RES_ROOT = UI_RES_ROOT + "icons/" ;
	private static final String FONT_RES_ROOT = UI_RES_ROOT + "fonts/" ;
	
	public static final String DB_ICON_RES              = ICON_RES_ROOT + "db.png"; 
	public static final String SAVE_TO_DB_ICON_RES      = ICON_RES_ROOT + "save_to_db.png";
	public static final String EXPORT_TO_EXCEL_ICON_RES = ICON_RES_ROOT + "export_to_excel.png";
	public static final String REFRESH_ICON_RES         = ICON_RES_ROOT + "refresh.png";
	public static final String ADD_NEW_STUDENT_RES      = ICON_RES_ROOT + "add_new_student.png";
	public static final String DELETE_STUDENT_RES       = ICON_RES_ROOT + "remove_student.png";
	public static final String ALERT_ICON_RES           = ICON_RES_ROOT + "alert.png";
	public static final String ERROR_ICON_RES           = ICON_RES_ROOT + "error.png";
	
	public static final String SAVE_BUTTON_CMD     = "Save"      ;
	public static final String EXPORT_BUTTON_CMD   = "Export"    ;
	public static final String REFRESH_BUTTON_CMD  = "Refresh"   ;
	public static final String NAME_BUTTON_CMD     = "Name"      ;
	public static final String ADD_NEW_STUDENT_CMD = "AddStudent"; 
	public static final String DELETE_STUDENT_CMD  = "DelStudent"; 
	
	private static String osName = null;
	
	static {
		getOSName();
		setSystemFont();
		setSystemLookAndFeel();
	}
	
	private static void getOSName() {
		osName = System.getProperty( "os.name" ).toLowerCase();		
	}
	
	private static void setSystemFont() {
		String pathName = null;
		if( osName.indexOf( "win" ) >= 0 ) {
			// Windows
			pathName = "win.ttf";
		}
		else if( osName.indexOf( "mac" ) >= 0 ) {
			// Macintosh
			pathName = "mac.ttf";
		}
		else if( osName.indexOf( "ix" ) >= 0 || osName.indexOf( "ux" ) >= 0 ) {
			// UNIX / Linux / AIX 
			pathName = "ubuntu.ttf";
		}

		if( pathName == null ) {
			FONT = new Font( "Helvetica", Font.PLAIN, 10 );
		}
		else {
			try {
				pathName = FONT_RES_ROOT + pathName ;
				InputStream is = UIConstants.class.getResourceAsStream( pathName ) ;
				FONT = Font.createFont( Font.TRUETYPE_FONT, is );
			} catch ( Exception e ) {
				e.printStackTrace();
			} 
		}
		System.setProperty( "awt.useSystemAAFontSettings", "on" );
		System.setProperty( "swing.aatext", "true" );
		
	}

	private static void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel( new PgsLookAndFeel() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
