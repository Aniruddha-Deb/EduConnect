package com.educonnect.admin.ui.buttons.impl;

import static com.educonnect.admin.ui.UIConstants.NAME_BUTTON_CMD;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.UIConstants;

public class NameButton extends JButton {

	private static final long serialVersionUID = -744490786481075169L;
	
	public NameButton() {
		super();
		setUpButton();
	}
	
	private void setUpButton() {
		super.setForeground( Color.WHITE );
		super.setHorizontalAlignment( SwingConstants.LEFT );
		super.setFont( UIConstants.FONT.deriveFont( Font.BOLD, 15f ) );
		super.setPreferredSize( new Dimension( 200, super.getHeight() ) );
		super.setBorder( new EmptyBorder( 0, 3, 0, 0 ) );		
		super.setActionCommand( NAME_BUTTON_CMD );
	}

	public void load() {
		super.setText( Constants.userName );
	}
}
