package com.educonnect.admin.ui;

import javax.swing.JFrame;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.panels.EditPanel;
import com.educonnect.common.bean.payload.DatabasePayload;
import com.educonnect.common.bean.payload.InfoPayload;

public class EditPanelQuickRun {

	public static void main( String[] args ) {
		AdminEngine engine = new AdminEngine();
		JFrame frame = new JFrame( "EditPanelQuickRun" );
		frame.setSize( 400, 400 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setLocationRelativeTo( null );
		EditPanel ep = new EditPanel();
		ep.load( new InfoPayload( "9A 9B 9C" ) );
		ep.display( new DatabasePayload( new String[]{"rollNo", "firstName", "lastName"} , 
										 new String[][]{
											{ "1", "Kushant", "Agarwal" }
										 } ) );
		frame.add( ep );
		frame.setVisible( true );
	}

}
