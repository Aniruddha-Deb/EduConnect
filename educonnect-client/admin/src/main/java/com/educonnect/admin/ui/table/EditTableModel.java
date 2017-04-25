package com.educonnect.admin.ui.table;

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

public class EditTableModel extends AbstractTableModel{
	
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
			editCopy.add( deepCopy( s ) );
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
					if( !( serverCopy.contains( gcStudent ) ) ) {
						if( editCopy.contains( gcStudent ) ) {
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
				// figure out what has been updated and where
				for( int i=0; i<serverCopy.size(); i++ ) {
					Student serverStudent = serverCopy.get(i);
					Student gcStudent     = goldenCopy.get(i);
					
					if( !( serverStudent.equals( gcStudent ) ) ) {
						if( serverStudent.getRollNo() != gcStudent.getRollNo() ) {
							if( editCopy.contains( gcStudent ) ) {
								Student editStudent = editCopy.get( editCopy.indexOf( gcStudent ) );
								editStudent.setRollNo( serverStudent.getRollNo() );
								gcStudent.setRollNo( serverStudent.getRollNo() );
							}
							else {
								JOptionPane.showMessageDialog( null, "Roll number conflict" );								
							}
						}
						if( !( serverStudent.getFirstName().equals( gcStudent.getFirstName() ) ) ) {
							if( editCopy.contains( gcStudent ) ) {
								Student editStudent = editCopy.get( editCopy.indexOf( gcStudent ) );
								editStudent.setFirstName( serverStudent.getFirstName() );
								System.out.println( gcStudent.getFirstName() );
								gcStudent.setFirstName( serverStudent.getFirstName() );
								System.out.println( gcStudent.getFirstName() );
							}
							else {
								JOptionPane.showMessageDialog( null, "First name conflict" );								
							}							
						}
						if( !( serverStudent.getLastName().equals( gcStudent.getLastName() ) ) ) {
							if( editCopy.contains( gcStudent ) ) {
								Student editStudent = editCopy.get( editCopy.indexOf( gcStudent ) );
								editStudent.setLastName( serverStudent.getLastName() );
								gcStudent.setLastName( serverStudent.getLastName() );
							}
							else {
								JOptionPane.showMessageDialog( null, "Last name conflict" );								
							}
						}
					}
				}
			}
			fireTableDataChanged();
		}
	}
	
	private Student deepCopy( Student s ) {
		Student student = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject( s );
			oos.flush();
			oos.close();
			bos.close();
			byte[] byteData = bos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			student = (Student)new ObjectInputStream(bais).readObject();
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		return student;
	}
	
	private void addToEditCopy( Student student ) {
		editCopy.add( deepCopy( student ) );
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
		Student previousStudent = editCopy.get( editCopy.size()-1 );
		editCopy.add( new Student( previousStudent.getRollNo()+1, null, null ) );
		fireTableDataChanged();
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
