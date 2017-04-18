package com.educonnect.admin.ui.panels.editpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.educonnect.admin.Constants;
import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.UIConstants;

public class NameButton extends JButton implements ActionListener{

	private static final long serialVersionUID = -744490786481075169L;
	private NameButtonPopupMenu menu = null;
	// TODO remove this when logout is implemented
	@SuppressWarnings("unused")
	private AdminEngine instance = null;
	
	public NameButton( AdminEngine instance ) {
		super();
		menu = new NameButtonPopupMenu();
		this.instance = instance;
		setUpButton();
	}
	
	private void setUpButton() {
		super.setForeground( Color.WHITE );
		super.setHorizontalAlignment( SwingConstants.LEFT );
		super.setFont( UIConstants.FONT.deriveFont( Font.BOLD, 15f ) );
		super.setBorder( new EmptyBorder( 0, 3, 0, 0 ) );		
		super.addActionListener( this );
	}

	public void load() {
		super.setText( Constants.userName );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		menu.show( this, 3, super.getHeight() );
	}
	
	private class NameButtonPopupMenu extends JPopupMenu implements ActionListener {

		private static final long serialVersionUID = 7766657697506325947L;
		private static final String LOGOUT_COMMAND = "Logout"; 
		
		NameButtonPopupMenu() {
			super();
			super.setFont( UIConstants.FONT.deriveFont( 12f ) );
			super.setBackground( Color.WHITE );
			super.add( LOGOUT_COMMAND );
		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			switch( e.getActionCommand() ) {
				case LOGOUT_COMMAND: 
					System.out.println( "Admin is trapped in the system! HAHAAAAAA" );
					// TODO jokes aside, implement logout through the engine.
			}
		}
		
	}
}
