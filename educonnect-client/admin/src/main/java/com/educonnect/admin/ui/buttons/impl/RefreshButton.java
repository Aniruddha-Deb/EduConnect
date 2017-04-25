package com.educonnect.admin.ui.buttons.impl;

import static com.educonnect.admin.ui.UIConstants.REFRESH_BUTTON_CMD;
import static com.educonnect.admin.ui.UIConstants.REFRESH_ICON_RES;

import javax.swing.ImageIcon;

import com.educonnect.admin.ui.util.UIUtils;

public class RefreshButton extends OptionPanelButton{

	private static final long serialVersionUID = 1856648779708206239L;
	
	public RefreshButton() {
		super( new ImageIcon( UIUtils.getImageResource( REFRESH_ICON_RES ) ) );
		super.setToolTipText( "Refresh the current table" );
		super.setActionCommand( REFRESH_BUTTON_CMD );
	}

}
