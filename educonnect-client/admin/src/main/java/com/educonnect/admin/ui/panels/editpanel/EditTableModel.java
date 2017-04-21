package com.educonnect.admin.ui.panels.editpanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.educonnect.common.bean.db.Student;

class EditTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 7094246511409713404L;
	private static final int  NUM_COLS = 3;
	private static final String[] headers  = { "roll no", "first name", "last name" };
	
	private List<Student> goldenCopy = null;
	private List<Student> editCopy   = null;
	private List<Student> serverCopy = null;
	
	private boolean goldenCopyPresent = false;
	
	public EditTableModel withStudents( Student[] students ) {
		this.goldenCopy = new ArrayList<Student>( Arrays.asList( students ) );
		goldenCopyPresent   = true;
		this.editCopy   = new ArrayList<>();
		editCopy.addAll( goldenCopy );
		return this;
	}
	
	public boolean isGoldenCopyPresent() {
		return goldenCopyPresent;
	}
	
	public void updateServerCopy( Student[] students ) {
		this.serverCopy = new ArrayList<Student>( Arrays.asList( students ) );
		
		if( !(serverCopy.equals( goldenCopy )) ) {
			if( serverCopy.size() > goldenCopy.size() ) {
				// There has been an addition in the server
				// figure out what has been added and where
				for( int i=0; i<serverCopy.size(); i++ ) {
					if( !( goldenCopy.contains( serverCopy.get(i) ) ) ) {
						System.out.println( "Server copy edited!" );
						goldenCopy.add( serverCopy.get(i) );
						addToEditCopy( serverCopy.get(i) );
					}
				}
			}
			else if( serverCopy.size() < goldenCopy.size() ) {
				// There has been a deletion in the server				
			}
			else {
				// There has been an update on an item
			}
		}
	}
	
	private void addToEditCopy( Student student ) {
		editCopy.add( student );
		Collections.sort( editCopy, new Comparator<Student>() {

			@Override
			public int compare( Student o1, Student o2 ) {
				if( o1.getRollNo() > o2.getRollNo() ) {
					return 1;
				}
				else if( o1.getRollNo() == o2.getRollNo() ) {
					return 0;
				}
				else {
					return -1;
				}
			}
		} );
		
		for( int i=0; i<editCopy.size(); i++ ) {
			Student s = editCopy.get(i); 
			setValueAt( s.getRollNo(),    i, 0 );
			setValueAt( s.getFirstName(), i, 1 );
			setValueAt( s.getLastName(),  i, 2 );
		}
	}
	
	@Override
	public int getRowCount() {
		return goldenCopy.size();
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
			return goldenCopy.get(rowIndex).getRollNo();
		case 1:
			return goldenCopy.get(rowIndex).getFirstName();
		case 2:
			return goldenCopy.get(rowIndex).getLastName();
		}
		
		return null;
	}

}
