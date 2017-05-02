package com.educonnect.admin.ui.panels.editpanel;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.buttons.ButtonFactory;
import com.educonnect.admin.ui.buttons.ButtonType;
import com.educonnect.admin.ui.buttons.OptionPanelButtonListener;
import com.educonnect.admin.ui.buttons.impl.NameButton;

public class OptionPanel extends JPanel{

	private static final long serialVersionUID = -1121562714415322399L;

	private ArrayList<JButton> optionButtons = null;
	private NameButton nameButton = null;
	
	public OptionPanel() {
		super();
		optionButtons = new ArrayList<>();
		setUpUI();
	}
	
	private void setUpUI() {
		super.setBackground( Color.BLACK );
		super.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );

		createAndAddNameButton();
		createAndAddGlue();
		createOptionButtons();
		
		addOptionButtonsToPanel();
	}
	
	private void addOptionButtonsToPanel() {
		for( JButton b : optionButtons ) {
			super.add( b );
		}
	}
	
	private void createOptionButtons() {
		createDeleteStudentButton();
		createAddNewStudentButton();
		createRefreshButton();
		createSaveButton();	
	}
	
	private void createAndAddNameButton() {
		nameButton = (NameButton)ButtonFactory.createOptionPanelButton( ButtonType.NAME );
		super.add( nameButton );
	}
	
	private void createAndAddGlue() {
		super.add( Box.createGlue() );
	}
	
	private void createDeleteStudentButton() {
		JButton deleteStudentButton = ButtonFactory.createOptionPanelButton( ButtonType.DELETE );
		optionButtons.add( deleteStudentButton );
	}
	
	private void createAddNewStudentButton() {
		JButton addNewStudentButton = ButtonFactory.createOptionPanelButton( ButtonType.ADD );
		optionButtons.add( addNewStudentButton );
	}
	
//	private void createAndAddExportButton() {
//		JButton exportButton = ButtonFactory.createOptionPanelButton( ButtonType.EXPORT );
//		optionButtons.add( exportButton );		
//	}
	
	private void createRefreshButton() {
		JButton refreshButton = ButtonFactory.createOptionPanelButton( ButtonType.REFRESH );
		optionButtons.add( refreshButton );
	}

	private void createSaveButton() {
		JButton saveButton = ButtonFactory.createOptionPanelButton( ButtonType.SAVE );
		optionButtons.add( saveButton );
	}
	
	public void loadNameOntoNameButton() {
		nameButton.setText( Constants.USER_NAME );
	}
	
	public void addOptionPaneButtonListener( OptionPanelButtonListener l ) {
		nameButton.addActionListener( l );
		
		for( JButton b : optionButtons ) {
			b.addActionListener( l );
		}
	}	
}
