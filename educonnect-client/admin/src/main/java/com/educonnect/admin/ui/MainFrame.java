package com.educonnect.admin.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.panels.EditPanel;
import com.educonnect.admin.ui.panels.LoginPanel;
import com.educonnect.admin.ui.panels.MainPanel;

public class MainFrame extends JFrame implements WindowListener{

	private static final long serialVersionUID = 2981649205219251588L;
	
	private LoginPanel loginPanel = null;
	private EditPanel editPanel = null;
	private MainPanel  mainPanel  = null;
	
	private static MainFrame instance = null;
	
	private CardLayout c = null;
	
	public MainFrame() {
		super( "EduConnect admin" );
		super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		super.setSize( 500, 400 );
		super.addWindowListener( this );
		super.setLocationRelativeTo( null );
		super.setBackground( new Color( 255, 255, 255 ) );
	
		instance = this;
		
		mainPanel = new MainPanel();
		editPanel = new EditPanel();
		loginPanel = new LoginPanel();
		
		c = (CardLayout)mainPanel.getLayout();
		
		mainPanel.add( loginPanel, "loginPanel" );
		mainPanel.add( editPanel, "editPanel" );
		super.add( mainPanel );
		
		setUIFont( new FontUIResource( UIConstants.FONT.deriveFont( 12f ) ) );
	}
	
    public static void setUIFont( FontUIResource f ) {
        Enumeration<?> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
	
	public void alert( String cause ) {
		JOptionPane.showMessageDialog( this, cause, "EduConnect admin", JOptionPane.ERROR_MESSAGE );
	}
	
	public static MainFrame getInstance() {
		return instance;
	}
	
	public void display() {
		super.setVisible( true );
	}
	
	public void showEditPanel() {
		editPanel.load();
		c.show( mainPanel, "editPanel" );
	}
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		AdminEngine.getInstance().shutdown();
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
