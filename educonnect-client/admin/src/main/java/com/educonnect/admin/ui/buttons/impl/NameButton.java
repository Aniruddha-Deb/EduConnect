package com.educonnect.admin.ui.buttons.impl;

import static com.educonnect.admin.ui.UIConstants.NAME_BUTTON_CMD;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.SwingConstants;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.UIConstants;

public class NameButton extends OptionPanelButton {

	private static final long serialVersionUID = -744490786481075169L;
	
	public NameButton() {
		super();
		setUpButton();
	}
	
	private void setUpButton() {
		super.setHorizontalAlignment( SwingConstants.LEFT );
		super.setFont( UIConstants.FONT.deriveFont( Font.BOLD, 15f ) );
		super.setPreferredSize( new Dimension( 200, super.getHeight() ) );
		super.setActionCommand( NAME_BUTTON_CMD );
	}

	public void load() {
		super.setText( Constants.userName );
	}
}
