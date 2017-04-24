package com.educonnect.admin.ui.buttons.impl;

import static com.educonnect.admin.ui.UIConstants.*;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import com.educonnect.admin.ui.util.UIUtils;

public class ExportButton extends JButton {

	private static final long serialVersionUID = 3855835775013616664L;
	
	public ExportButton() {
		super( new ImageIcon( UIUtils.getImageResource( EXPORT_TO_EXCEL_ICON_RES ) ) );
		super.setToolTipText( "Export all tables to an excel sheet" );
		super.setBackground( Color.BLACK );
		super.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		super.setActionCommand( EXPORT_BUTTON_CMD );
	}

}
