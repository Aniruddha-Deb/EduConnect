package com.educonnect.admin.ui.buttons;

import javax.swing.JButton;

import com.educonnect.admin.ui.buttons.impl.ExportButton;
import com.educonnect.admin.ui.buttons.impl.NameButton;
import com.educonnect.admin.ui.buttons.impl.RefreshButton;
import com.educonnect.admin.ui.buttons.impl.SaveButton; 

public class ButtonFactory {

	public static JButton createButton( ButtonType bType ) {
		
		switch( bType ) {
			case EXPORT:
				return new ExportButton();
				
			case NAME:
				return new NameButton();
			
			case REFRESH:
				return new RefreshButton();
				
			case SAVE:
				return new SaveButton();
				
			default:
				return null;
		}
	}
}
