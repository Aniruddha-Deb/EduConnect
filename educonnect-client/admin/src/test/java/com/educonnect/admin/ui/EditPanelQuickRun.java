package com.educonnect.admin.ui;

import javax.swing.JFrame;

import com.educonnect.admin.ui.panels.EditPanel;

public class EditPanelQuickRun {

	public static void main( String[] args ) {
		
		JFrame frame = new JFrame( "EditPanelQuickRun" );
		frame.setSize( 400, 400 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setLocationRelativeTo( null );
		EditPanel ep = new EditPanel();
		ep.load();
		frame.add( ep );
		frame.setVisible( true );
	}

}
