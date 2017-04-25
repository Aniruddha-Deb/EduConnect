package com.educonnect.admin.ui.buttons.impl;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class OptionPanelButton extends JButton{

	private static final long serialVersionUID = -8593426523711333519L;
	
	public OptionPanelButton() {
		super();
		setUpUI();
	}
	
	public OptionPanelButton( ImageIcon icon ) {
		super( icon );
		setUpUI();
	}
	
	private void setUpUI() {
		super.setBackground( Color.BLACK );
		super.setBorderPainted( false );
		super.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		super.setOpaque( false );
		super.setForeground( Color.WHITE );
		super.setContentAreaFilled( true );
	}

	@Override
	protected void paintComponent( Graphics g ) {
		
		if( getModel().isPressed() ) {
			g.setColor( Color.BLACK );
		}
		else if( getModel().isRollover() ) {
			g.setColor( Color.BLACK );
		}
		else if( getModel().isArmed() ) {
			g.setColor( Color.BLACK );
		}
		else if( getModel().isSelected() ) {
			g.setColor( Color.BLACK );
		}
		
		super.paintComponent(g);
	}
}
