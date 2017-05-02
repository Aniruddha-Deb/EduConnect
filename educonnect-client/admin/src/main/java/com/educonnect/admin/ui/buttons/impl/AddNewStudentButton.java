package com.educonnect.admin.ui.buttons.impl;

import static com.educonnect.admin.ui.UIConstants.*;

import javax.swing.ImageIcon;

import com.educonnect.admin.ui.util.UIUtils;

public class AddNewStudentButton extends OptionPanelButton {

	private static final long serialVersionUID = -1425630061156932841L;

	public AddNewStudentButton() {
		super( new ImageIcon( UIUtils.getImageResource( ADD_NEW_STUDENT_RES ) ) );
		super.setToolTipText( "Add a new student to the class" );
		super.setActionCommand( ADD_NEW_STUDENT_CMD );
	}
}
