package com.educonnect.admin.ui;

import java.awt.Color;

import javax.swing.JFrame;

import com.educonnect.admin.ui.panels.LoginPanel;
import com.educonnect.common.bean.payload.Payload;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 2981649205219251588L;
	
	private LoginPanel loginPanel = null;
	
	public MainFrame() {
		super( "EduConnect" );
		super.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		super.setSize( 500, 400 );
		super.setLocationRelativeTo( null );
		super.setBackground( new Color( 255, 255, 255 ) );
	
		loginPanel = new LoginPanel();
		super.add( loginPanel );
	}

	public void display() {
		super.setVisible( true );
	}
	
	public void updateUI( Payload p ) {
		
	}
}
