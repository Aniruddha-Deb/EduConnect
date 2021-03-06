package com.educonnect.admin.ui.menu;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.educonnect.admin.ui.UIConstants;

public class NameButtonPopupMenu extends JPopupMenu{

	private static final long serialVersionUID = 7766657697506325947L;
	public static final String LOGOUT_COMMAND = "Logout";
	
	private JMenuItem logout = null;
	
	public NameButtonPopupMenu() {
		super();
		super.setFont( UIConstants.FONT.deriveFont( 12f ) );
		super.setBackground( Color.WHITE );
		
		logout = new JMenuItem( LOGOUT_COMMAND );
		super.add( logout );
	}
	
	public void addActionListener( ActionListener menuListener ) {
		logout.addActionListener( menuListener );
	}
}
