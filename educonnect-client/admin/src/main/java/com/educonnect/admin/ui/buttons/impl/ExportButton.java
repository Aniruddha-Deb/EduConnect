package com.educonnect.admin.ui.buttons.impl;

import static com.educonnect.admin.ui.UIConstants.EXPORT_BUTTON_CMD;
import static com.educonnect.admin.ui.UIConstants.EXPORT_TO_EXCEL_ICON_RES;

import javax.swing.ImageIcon;

import com.educonnect.admin.ui.util.UIUtils;

public class ExportButton extends OptionPanelButton {

	private static final long serialVersionUID = 3855835775013616664L;
	
	public ExportButton() {
		super( new ImageIcon( UIUtils.getImageResource( EXPORT_TO_EXCEL_ICON_RES ) ) );
		super.setToolTipText( "Export all tables to an excel sheet" );
		super.setActionCommand( EXPORT_BUTTON_CMD );
	}

}
