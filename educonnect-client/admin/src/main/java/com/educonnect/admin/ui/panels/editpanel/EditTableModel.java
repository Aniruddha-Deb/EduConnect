package com.educonnect.admin.ui.panels.editpanel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;
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
		for( Student s : goldenCopy ) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject( s );
				oos.flush();
				oos.close();
				bos.close();
				byte[] byteData = bos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
				editCopy.add( (Student)new ObjectInputStream(bais).readObject() );
			} catch( Exception ex ) {
				ex.printStackTrace();
			}
		}
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
				// figure out what has been deleted and where
				// if the row deleted has not been edited, then just delete it.
				// if the row has been edited, show a popup
				System.out.println( "There's been a delete" ); 
				for( int i=0; i<goldenCopy.size(); i++ ) {
					Student gcStudent = goldenCopy.get(i);
					System.out.println( gcStudent.getRollNo() );
					System.out.println( gcStudent.getFirstName() );
					System.out.println( gcStudent.getLastName() );
					if( !( serverCopy.contains( gcStudent ) ) ) {
						if( editCopy.contains( gcStudent ) ) {
							System.out.println( "index > -1" );
							System.out.println( editCopy.get( editCopy.indexOf( gcStudent ) ).getRollNo() );
							System.out.println( editCopy.get( editCopy.indexOf( gcStudent ) ).getFirstName() );
							System.out.println( editCopy.get( editCopy.indexOf( gcStudent ) ).getLastName() );
							editCopy.remove( editCopy.indexOf( gcStudent ) );
							goldenCopy.remove( i );
						}
						else {
							JOptionPane.showMessageDialog( null, "You're screwed!" );
						}
					}
				}
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
	
	public void addRow( int rowIndex ) {
		editCopy.add( new Student( -1, null, null ) );
		Student addedStudent = editCopy.get( editCopy.size()-1 );
		setValueAt( "", rowIndex, 0 );
		setValueAt( addedStudent.getFirstName(), rowIndex, 1 );
		setValueAt( addedStudent.getLastName() , rowIndex, 2 );
	}
	
	@Override
	public int getRowCount() {
		return editCopy.size();
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
		Student s = editCopy.get( rowIndex );
		System.out.println( "Got a student with name " + s.getFirstName() );
		switch ( columnIndex ) {
		case 0:
			try {
				s.setRollNo( Integer.parseInt( (String)aValue ) );
			} catch( NumberFormatException ex ) {
				try {
					s.setRollNo( editCopy.get( rowIndex-1 ).getRollNo() + 1 );
				} catch( ArrayIndexOutOfBoundsException e ) {
					s.setRollNo( editCopy.get( rowIndex ).getRollNo() + 1 );					
				}
			} catch( ClassCastException exe ) {
				s.setRollNo( ((Integer)aValue).intValue() );
			}
			
			break;
		case 1:
			s.setFirstName( (String)aValue );
			break;
		case 2:
			s.setLastName( (String)aValue );
			break;
		}
		fireTableCellUpdated( rowIndex, columnIndex );
	}
	
	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {
		
		switch( columnIndex ) {
		case 0:
			return editCopy.get(rowIndex).getRollNo();
		case 1:
			return editCopy.get(rowIndex).getFirstName();
		case 2:
			return editCopy.get(rowIndex).getLastName();
		}
		
		return null;
	}
}
