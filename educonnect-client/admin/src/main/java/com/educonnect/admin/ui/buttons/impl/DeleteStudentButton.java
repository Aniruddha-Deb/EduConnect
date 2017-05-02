package com.educonnect.admin.ui.buttons.impl;

import static com.educonnect.admin.ui.UIConstants.*;

import javax.swing.ImageIcon;

import com.educonnect.admin.ui.util.UIUtils;

public class DeleteStudentButton extends OptionPanelButton {

	private static final long serialVersionUID = -5245906994987382845L;

	public DeleteStudentButton() {
		super( new ImageIcon( UIUtils.getImageResource( DELETE_STUDENT_RES ) ) );
		super.setToolTipText( "Delete the current student entry" );
		super.setActionCommand( DELETE_STUDENT_CMD );	
	}

}
