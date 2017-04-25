package com.educonnect.admin.ui.buttons;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import com.educonnect.admin.ui.buttons.impl.*; 

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
		
		buttonToReturn.setBackground( Color.BLACK );
		buttonToReturn.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		buttonToReturn.setForeground( Color.WHITE );
		return buttonToReturn;
	}
}
