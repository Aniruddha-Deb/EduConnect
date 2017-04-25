package com.educonnect.admin.ui.buttons.impl;

import static com.educonnect.admin.ui.UIConstants.SAVE_BUTTON_CMD;
import static com.educonnect.admin.ui.UIConstants.SAVE_TO_DB_ICON_RES;

import javax.swing.ImageIcon;

import com.educonnect.admin.ui.util.UIUtils;

public class SaveButton extends OptionPanelButton {

	private static final long serialVersionUID = 8513126563922347687L;
	
	public SaveButton() {
		super( new ImageIcon( UIUtils.getImageResource( SAVE_TO_DB_ICON_RES ) ) );
		super.setToolTipText( "Save all tables to the database" );
		super.setActionCommand( SAVE_BUTTON_CMD );
	}

}
