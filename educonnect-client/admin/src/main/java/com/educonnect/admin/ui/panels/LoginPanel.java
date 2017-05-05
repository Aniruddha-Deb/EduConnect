package com.educonnect.admin.ui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import com.educonnect.admin.engine.AdminEngine;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.admin.ui.util.UIUtils;

public class LoginPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 8803272978255150804L;
	
	private JButton        loginButton         = null;
	private JPasswordField passwordField       = null;
	private JTextField     emailIdField        = null;
	private JLabel         loginLabel          = null;
	private JLabel         passwordPromptLabel = null;
	private JLabel         emailIdPromptLabel  = null;
	
	private AdminEngine instance = null;
	
	@SuppressWarnings("serial")
	public LoginPanel( AdminEngine instance ) {
		super();
		super.setLayout( new GridBagLayout() );
		super.setBorder( new EmptyBorder( 75, 75, 75, 75 ) );
		super.setBackground( Color.WHITE );
		super.getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT )
			 .put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER , 0 ), "Login" );
		super.getActionMap().put( "Login", new AbstractAction() {
			
			@Override
			public void actionPerformed( ActionEvent e ) {
				loginButton.doClick();
				
			}
		});
		
		this.instance = instance;
		
		setUpUI();
	}
	
	private void setUpUI() {
		createLoginLabel();
		createEmailIdPromptLabel();
		createEmailIdField();
		createVerticalStrut();
		createPasswordPromptLabel();
		createPasswordField();
		createLoginButton();
	}

	private void createLoginLabel() {
		GridBagConstraints c = new GridBagConstraints();
		loginLabel = new JLabel( "Login: " );
		loginLabel.setFont( UIConstants.FONT.deriveFont( 40f ) );
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.NORTHWEST;
		super.add( loginLabel, c );		
	}
		
	private void createEmailIdPromptLabel() {
		GridBagConstraints c = new GridBagConstraints();
		emailIdPromptLabel = new JLabel( "Email id: " );
		emailIdPromptLabel.setFont( UIConstants.FONT.deriveFont( 20f ) );
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		super.add( emailIdPromptLabel, c );		
	}
	
	private void createEmailIdField() {
		GridBagConstraints c = new GridBagConstraints();
		if( UIUtils.isSerialized( "emailIdField" ) ) {
			emailIdField = (JTextField)UIUtils.deserialize( "emailIdField" );
		}
		else {
			emailIdField = new JTextField();
		}
		emailIdField.setBorder( BorderFactory.createMatteBorder( 0, 0, 1, 0, Color.GRAY ) );
		emailIdField.setFont( UIConstants.FONT.deriveFont( 15f ) );
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		super.add( emailIdField, c );
	}
	
	private void createVerticalStrut() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		super.add( Box.createVerticalStrut( 10 ), c );

	}
	
	private void createPasswordPromptLabel() {
		GridBagConstraints c = new GridBagConstraints();
		passwordPromptLabel = new JLabel( "Password: " );
		passwordPromptLabel.setFont( UIConstants.FONT.deriveFont( 20f ) );
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		super.add( passwordPromptLabel, c );
	}
	
	private void createPasswordField() {
		GridBagConstraints c = new GridBagConstraints();
		passwordField = new JPasswordField();
		passwordField.setBorder( BorderFactory.createMatteBorder( 0, 0, 1, 0, Color.GRAY ) );
		passwordField.setFont( UIConstants.FONT.deriveFont( 15f ) );
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		super.add( passwordField, c );
	}
	
	private void createLoginButton() {
		GridBagConstraints c = new GridBagConstraints();
		loginButton = new JButton( "Log in" );
		loginButton.addActionListener( this );
		loginButton.setFont( UIConstants.FONT.deriveFont( 20f ) );
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 0.5;
		c.gridwidth = 2;
		c.ipadx = 4;
		c.ipady = 5;
		c.anchor = GridBagConstraints.SOUTH;
		super.add( loginButton, c );
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		UIUtils.serialize( emailIdField, "emailIdField" );
		try {
			instance.login( emailIdField.getText(), 
									   passwordField.getPassword()  
								 	 );
		} catch ( Exception e1 ) {
			e1.printStackTrace();
		}
		passwordField.setText( null );
	}
}
