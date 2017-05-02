package com.educonnect.admin.ui.table;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.educonnect.admin.ui.util.UIUtils;
import com.educonnect.common.message.dbclass.Student;

public class EditTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 7094246511409713404L;
	private static final int  NUM_COLS = 3;
	private static final String[] headers  = { "roll no", "first name", "last name" };
	
	private List<Student> goldenCopy = null;
	private List<Student> editCopy   = null;
	private List<Student> serverCopy = null;
	
	private Comparator<Student> studentComparator = null;
	
	public EditTableModel withStudents( Student[] students ) {
		this.goldenCopy = new ArrayList<Student>( Arrays.asList( students ) );
		this.editCopy   = new ArrayList<>();
		
		for( Student s : goldenCopy ) {
			editCopy.add( deepCopy( s ) );
		}
		
		createStudentComparator();
		editCopy.sort( studentComparator );
		return this;
	}
	
	private void createStudentComparator() {
		studentComparator = new Comparator<Student>() {
			
			@Override
			public int compare(Student o1, Student o2) {
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
		};
	}
	
	public boolean unsavedChangesPresent() {
		if( editCopy.size() != goldenCopy.size() ) {
			return true;
		}
		
		for( Student s : editCopy ) {
			if( !( goldenCopy.contains( s ) ) ) {
				return true;
			}
		}
		
		return false;
	}
	
	public List<Student> getDirtyStudents() {
		List<Student> dirtyStudents = new ArrayList<>();
		
		for( Student s : editCopy ) {
			if( !( goldenCopy.contains( s ) ) ) {
				dirtyStudents.add( s );
			}
		}
		this.goldenCopy = new ArrayList<>();
		
		for( Student s : editCopy ) {
			goldenCopy.add( deepCopy( s ) );
		}
		return dirtyStudents;
	}
	
	public void updateServerCopy( Student[] students ) {
		this.serverCopy = new ArrayList<Student>( Arrays.asList( students ) );
		
		if( !(serverCopy.equals( goldenCopy )) ) {
			if( serverCopy.size() > goldenCopy.size() ) {
				addServerCopyChangesToGoldenCopy();
			}
			else if( serverCopy.size() < goldenCopy.size() ) {
				deleteServerCopyChangesFromGoldenCopy();
			}
			else {
				updateGoldenCopy();
			}
			fireTableDataChanged();
		}
	}
	
	private void addServerCopyChangesToGoldenCopy() {
		for( int i=0; i<serverCopy.size(); i++ ) {
			if( !( goldenCopy.contains( serverCopy.get(i) ) ) ) {
				goldenCopy.add( serverCopy.get(i) );
				addToEditCopy( serverCopy.get(i) );
			}
		}
	}
	
	private void deleteServerCopyChangesFromGoldenCopy() {
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
	
	private void updateGoldenCopy() {
		for( int i=0; i<serverCopy.size(); i++ ) {
			Student serverStudent = serverCopy.get(i);
			Student gcStudent     = goldenCopy.get(i);
			
			if( !( serverStudent.equals( gcStudent ) ) ) {
				if( serverStudent.getUID() != gcStudent.getUID() ) {
					updateStudentUID( serverStudent, gcStudent );
				}
				if( serverStudent.getRollNo() != gcStudent.getRollNo() ) {
					updateStudentRollNumber( serverStudent, gcStudent );
				}
				if( !( serverStudent.getFirstName().equals( gcStudent.getFirstName() ) ) ) {
					updateStudentFirstName( serverStudent, gcStudent );
				}
				if( !( serverStudent.getLastName().equals( gcStudent.getLastName() ) ) ) {
					updateStudentLastName( serverStudent, gcStudent );
				}
			}
		}
	}
	
	private void updateStudentUID( Student serverStudent, Student gcStudent ) {
		for( Student s : editCopy ) {
			if( s.getFirstName().equals( gcStudent.getFirstName() ) && 
				s.getLastName().equals( gcStudent.getLastName() ) && 
				s.getRollNo() == gcStudent.getRollNo() ) {
				
				s.setUID( serverStudent.getUID() );
				gcStudent.setUID( serverStudent.getUID() );
			}
		}		
	}
	
	private void updateStudentRollNumber( Student serverStudent, Student gcStudent ) {
		if( editCopy.contains( gcStudent ) ) {
			Student editStudent = editCopy.get( editCopy.indexOf( gcStudent ) );
			editStudent.setRollNo( serverStudent.getRollNo() );
			gcStudent.setRollNo( serverStudent.getRollNo() );
		}
		else {
			JOptionPane.showMessageDialog( null, "Roll number conflict" );								
		}
	}
	
	private void updateStudentFirstName( Student serverStudent, Student gcStudent ) {
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
	
	private void updateStudentLastName( Student serverStudent, Student gcStudent ) {
		if( editCopy.contains( gcStudent ) ) {
			Student editStudent = editCopy.get( editCopy.indexOf( gcStudent ) );
			editStudent.setLastName( serverStudent.getLastName() );
			gcStudent.setLastName( serverStudent.getLastName() );
		}
		else {
			JOptionPane.showMessageDialog( null, "Last name conflict" );								
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
		editCopy.sort( studentComparator );		
	}
	
	public void addRow( int rowIndex ) {
		Student previousStudent = null;
		
		if( editCopy.size() > 0 ) {
			previousStudent = editCopy.get( editCopy.size()-1 );
		}
		
		if( previousStudent != null ) {
			editCopy.add( new Student( -1, previousStudent.getRollNo()+1, "", "" ) );
		}
		else {
			editCopy.add( new Student( -1, 1, "", "" ) );			
		}
		fireTableDataChanged();
	}
	
	public void deleteRow( int rowIndex ) {
		if( rowIndex == -1 ) {
			UIUtils.showError( null, "No row is selected" );
		}
		else {
			editCopy.remove( rowIndex );
			fireTableDataChanged();
		}
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
