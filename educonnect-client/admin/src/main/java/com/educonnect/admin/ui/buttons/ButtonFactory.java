package com.educonnect.admin.ui.buttons;

import javax.swing.JButton;

import com.educonnect.admin.ui.buttons.impl.ExportButton;
import com.educonnect.admin.ui.buttons.impl.NameButton;
import com.educonnect.admin.ui.buttons.impl.RefreshButton;
import com.educonnect.admin.ui.buttons.impl.SaveButton; 

public class ButtonFactory {

	public static JButton createOptionPanelButton( ButtonType bType ) {
		
		JButton buttonToReturn = null;
		
		switch( bType ) {
			case EXPORT:
				buttonToReturn = new ExportButton();
			break;
				
			case NAME:
				buttonToReturn = new NameButton();
			break;
			
			case REFRESH:
				buttonToReturn = new RefreshButton();
			break;
				
			case SAVE:
				buttonToReturn = new SaveButton();
			break;
		}
		
		return buttonToReturn;
	}
}
