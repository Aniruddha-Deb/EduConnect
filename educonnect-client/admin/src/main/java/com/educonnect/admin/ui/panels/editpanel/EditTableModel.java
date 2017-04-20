package com.educonnect.admin.ui.panels.editpanel;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.educonnect.common.bean.db.Student;

public class EditTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 7094246511409713404L;
	private static final int  NUM_COLS = 3;
	private List<Student> students = null;
	private String[]  headers  = { "roll no", "first name", "last name" };
	
	public EditTableModel( Student[] students ) {
		this.students = Arrays.asList( students );
	}
	
	@Override
	public int getRowCount() {
		return students.size();
	}

	@Override
	public int getColumnCount() {
		return NUM_COLS;
	}
	
	@Override
	public String getColumnName( int column ) {
		return headers[column];
	}
	
	@Override
	public boolean isCellEditable( int rowIndex, int columnIndex ) {
		return true;
	}
	
	@Override
	public void setValueAt( Object aValue, int rowIndex, int columnIndex ) {
		super.setValueAt( aValue, rowIndex, columnIndex );
		fireTableCellUpdated( rowIndex, columnIndex );
	}

	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {
		
		switch( columnIndex ) {
		case 0:
			return students.get(rowIndex).getRollNo();
		case 1:
			return students.get(rowIndex).getFirstName();
		case 2:
			return students.get(rowIndex).getLastName();
		}
		
		return null;
	}

}
