package com.educonnect.admin.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.panels.LoginPanel;
import com.educonnect.admin.ui.panels.MainPanel;
import com.educonnect.admin.ui.panels.editpanel.EditPanel;

import static com.educonnect.admin.ui.UIConstants.*;

public class MainFrame extends JFrame implements WindowListener{

	private static final long serialVersionUID = 2981649205219251588L;

	private MainPanel  mainPanel  = null;
	private LoginPanel loginPanel = null;
	private EditPanel  editPanel  = null;
		
	private CardLayout c = null;
	
	private AdminEngine instance = null;
	
	public MainFrame( AdminEngine instance ) {
		super( "EduConnect admin" );

		this.instance = instance;
		
		setUpUI();
	}
	
	private void setUpUI() {
		setUpFrame();
		setUIDefaults();
		createPanels();
		createCardLayout();
		addMainPanel();
	}
	
	private void setUpFrame() {
		super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		super.setSize( 500, 400 );
		super.addWindowListener( this );
		super.setLocationRelativeTo( null );
		super.setBackground( new Color( 255, 255, 255 ) );
	}
	
	private void setUIDefaults() {
		UIManager.getLookAndFeelDefaults().put( "Panel.background", Color.WHITE );
		UIManager.getLookAndFeelDefaults().put( "TextField.background", Color.WHITE );
		UIManager.getLookAndFeelDefaults().put( "Button.background", Color.WHITE );
		UIManager.getLookAndFeelDefaults().put( "Label.background", Color.WHITE );
		UIManager.getLookAndFeelDefaults().put( "Label.font", UIConstants.FONT.deriveFont( 12f ) );
	}
	
	private void createPanels() {
		mainPanel = new MainPanel();
		editPanel = new EditPanel( instance );
		loginPanel = new LoginPanel( instance );
	}

	private void createCardLayout() {
		c = (CardLayout)mainPanel.getLayout();
	}
	
	private void addMainPanel() {
		mainPanel.add( loginPanel, LOGIN_PANEL );
		mainPanel.add( editPanel, EDIT_PANEL );
		super.add( mainPanel );
	}
	
	public EditPanel getEditPanel() {
		return editPanel;
	}
	
	public MainPanel getMainPanel() {
		return mainPanel;
	}
	
	public void showPanel( String panelName ) {
		System.out.println( "Showing panel " + panelName );
		c.show( mainPanel, panelName );
		mainPanel.revalidate();
	}
	
	public void showPrevious() {
		c.previous( mainPanel );
	}
	
	public void showNext() {
		c.next( mainPanel );
	}
	
	public void display() {
		super.setVisible( true );
	}
	
	//------------------- WindowAdapter methods --------------------------------
	
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println( "Close button pressed" ); 
		instance.shutdown();
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
