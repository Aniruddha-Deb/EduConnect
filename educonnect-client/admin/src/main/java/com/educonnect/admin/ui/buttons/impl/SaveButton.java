package com.educonnect.admin.ui.buttons.impl;

import static com.educonnect.admin.ui.UIConstants.*;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import com.educonnect.admin.ui.util.UIUtils;

public class SaveButton extends JButton {

	private static final long serialVersionUID = 8513126563922347687L;
	
	public SaveButton() {
		super( new ImageIcon( UIUtils.getImageResource( SAVE_TO_DB_ICON_RES ) ) );
		super.setToolTipText( "Refresh the current table" );
		super.setBackground( Color.BLACK );
		super.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		super.setActionCommand( SAVE_BUTTON_CMD );
	}

}
