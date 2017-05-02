package com.educonnect.admin.ui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.educonnect.admin.ui.UIConstants.*;

public interface OptionPanelButtonListener extends ActionListener {
	
	@Override
	default void actionPerformed( ActionEvent e ) {
		switch( e.getActionCommand() ) {
			case SAVE_BUTTON_CMD:
				onSaveButtonClicked();
			break;
			
//			case EXPORT_BUTTON_CMD:
//				onExportButtonClicked();
//			break;
			
			case REFRESH_BUTTON_CMD:
				onRefreshButtonClicked();
			break;
			
			case NAME_BUTTON_CMD:
				onNameButtonClicked();
			break;
			
			case ADD_NEW_STUDENT_CMD:
				onAddStudentButtonClicked();
			break;
			
			case DELETE_STUDENT_CMD:
				onDeleteStudentButtonClicked();
			break;
		}
	}
	
	void onSaveButtonClicked();
	
//	void onExportButtonClicked();
	
	void onRefreshButtonClicked();
	
	void onNameButtonClicked();
	
	void onAddStudentButtonClicked();
	
	void onDeleteStudentButtonClicked();
}
