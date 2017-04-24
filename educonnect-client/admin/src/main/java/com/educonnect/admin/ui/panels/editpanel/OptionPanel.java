package com.educonnect.admin.ui.panels.editpanel;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.buttons.ButtonFactory;
import com.educonnect.admin.ui.buttons.ButtonType;
import com.educonnect.admin.ui.buttons.OptionPanelButtonListener;

public class OptionPanel extends JPanel{

	private static final long serialVersionUID = -1121562714415322399L;
	
	private JButton nameButton    = null;
	private JButton exportButton  = null;
	private JButton refreshButton = null;
	private JButton saveButton    = null;
	
	public OptionPanel() {
		super();
		setUpUI();
	}
	
	private void setUpUI() {
		super.setBackground( Color.BLACK );
		super.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );

		createAndAddNameButton();
		createAndAddGlue();
		createAndAddRefreshButton();
		createAndAddSaveButton();		
		createAndAddExportButton();
	}
	
	private void createAndAddNameButton() {
		nameButton = ButtonFactory.createButton( ButtonType.NAME );
		super.add( nameButton );		
	}
	
	private void createAndAddGlue() {
		super.add( Box.createGlue() );
	}
	
	private void createAndAddExportButton() {
		exportButton = ButtonFactory.createButton( ButtonType.EXPORT );
		super.add( exportButton );		
	}
	
	private void createAndAddRefreshButton() {
		refreshButton = ButtonFactory.createButton( ButtonType.REFRESH );
		super.add( refreshButton );
	}

	private void createAndAddSaveButton() {
		saveButton = ButtonFactory.createButton( ButtonType.SAVE );
		super.add( saveButton );
	}
	
	public void loadNameOntoNameButton() {
		nameButton.setText( Constants.userName );
	}
	
	public void addOptionPaneButtonListener( OptionPanelButtonListener l ) {
		nameButton.addActionListener( l );
		exportButton.addActionListener( l );
		refreshButton.addActionListener( l );
		saveButton.addActionListener( l );
	}	
}
