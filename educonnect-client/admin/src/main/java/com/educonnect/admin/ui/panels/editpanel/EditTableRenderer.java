package com.educonnect.admin.ui.panels.editpanel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class EditTableRenderer implements TableCellRenderer{
	
	TableCellRenderer render;
	
	EditTableRenderer( JTable t ) {
		this.render = t.getDefaultRenderer( Object.class );
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		JComponent result = (JComponent)render.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if( isSelected ) {
			result.setBackground( Color.LIGHT_GRAY );
			result.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
		}
		else {
			result.setBackground( Color.WHITE );
			result.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
		}
		
		if( hasFocus ) {
			result.setBackground( new Color( 92, 170, 248) );
			result.setForeground( Color.WHITE );
			result.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
		}
		else {
			result.setForeground( Color.BLACK );				
			result.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
		}
		
		return result;
	}
}
