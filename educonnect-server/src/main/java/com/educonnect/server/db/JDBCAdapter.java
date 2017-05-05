package com.educonnect.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.dbclass.Student;
import com.educonnect.common.message.dbupdate.Row;

public class JDBCAdapter {
	
	private static final Logger log = Logger.getLogger( JDBCAdapter.class );

	private static JDBCAdapter instance = null;
	private JDBCConnectionPool connPool = null;
	
	public static JDBCAdapter getInstance() {
		if( instance == null ) {
			instance = new JDBCAdapter();
		}
		return instance;
	}
	
	private JDBCAdapter() {
		try {
			connPool = new JDBCConnectionPool( "com.mysql.cj.jdbc.Driver" ,
									"jdbc:mysql://localhost:3306/ec_tbs_camp?useSSL=false",
									"root",
									"bokor123" );
		} catch ( Exception e ) {
			log.error( "Could not create new Connection Pool", e );
		}
	}
	
	public int getClient( String emailId, char[] password, ClientType clientType ) {
		int clientUID = -1;
		
		String query = getClientRetrievalQuery( clientType );
		
		Connection c = null;
		try {
			c = connPool.getConnection();
			PreparedStatement st = c.prepareStatement( query );
			st.setString( 1, emailId );
			st.setString( 2, new String( password ) );
			st.setString( 3, new String( password ) );
			Arrays.fill( password, '0' );			
			ResultSet rs = st.executeQuery();
			rs.next();
			
			try {
				clientUID = rs.getInt( "UID" );
			} catch( SQLException ex ) {
				clientUID = -1;
			}
		} catch( Exception e ) {
			log.error( "Could not retrieve client from database", e );
		} finally {
			connPool.returnConnection( c );
		}
		return clientUID;
	}
	
	private String getClientRetrievalQuery( ClientType clientType ) {
		switch( clientType ) {
		case ADMIN:
			log.debug( "Retrieving an AdminClient from the database" );
			return "SELECT UID FROM admins "
			 		   + "WHERE emailId = ? "
			 		   + "AND passwd = AES_ENCRYPT( ?, ? ) ";

		case STUDENT:
			log.debug( "Retrieving a StudentClient from the database" );
			return "SELECT UID FROM students "
			 		   + "WHERE emailId = ? "
			 		   + "AND passwd = AES_ENCRYPT( ?, ? ) ";
		}
		return null;
	}

	public String getAdminName( int UID ) {
		String adminName = null;
		final String query = "SELECT name FROM admins WHERE UID = ?";
		
		Connection c = null;
		try {
			c = connPool.getConnection();
			PreparedStatement st = c.prepareStatement( query );
			st.setInt( 1, UID );
			ResultSet rs = st.executeQuery();
			rs.next();
			adminName = rs.getString( "name" );
		} catch( Exception e ) {
			log.error( "Could not get admin name", e );
		} finally {
			connPool.returnConnection( c );
		}
		return adminName;		
	}
	
	public String getStudentName( int UID ) {
		String studentName = null;
		final String query = "SELECT firstName, lastName FROM students WHERE UID = ?";
		
		Connection c = null;
		try {
			c = connPool.getConnection();
			PreparedStatement st = c.prepareStatement( query );
			st.setInt( 1, UID );
			ResultSet rs = st.executeQuery();
			rs.next();
			studentName = rs.getString( "firstName" ) + " " + 
						  rs.getString( "lastName" );
			
		} catch( Exception e ) {
			log.error( "Could not get student name", e );
		} finally {
			connPool.returnConnection( c );
		}
		return studentName;		
	}

	public void updateRow( Row r, int clazz, char section ) {

		switch( r.getAction() ) {
			case CREATE:
				log.debug( "Creating a student with First Name " + r.getStudent().getFirstName() );
				createStudent( clazz, section, r.getStudent() );
			break;
			
			case DELETE:
				log.debug( "Deleting student no." + r.getStudent().getUID() );
				deleteStudent( r.getStudent() );				
			break;
			
			case UPDATE:
				log.debug( "Updating student no." + r.getStudent().getUID() );
				updateStudent( clazz, section, r.getStudent() );
			break;
		}
	}
	
	private void createStudent( int clazz, char section, Student s ) {
		final String update = "INSERT INTO students " + 
					 "(class, section, rollNo, firstName, lastName) " + 
					 "VALUES ( ?, ?, ?, ?, ? ) " ;
		
		Connection c = null;
		try {
			c = connPool.getConnection();
			PreparedStatement st = c.prepareStatement( update );
			st.setInt( 1, clazz );
			st.setString( 2, new String( new char[]{ section } ) );
			st.setInt( 3, s.getRollNo() );
			st.setString( 4, s.getFirstName() );
			st.setString( 5, s.getLastName() );
			st.executeUpdate();
			
		} catch( Exception e ) {
			log.error( "Unable to create new student in the database", e );
		} finally {
			connPool.returnConnection( c );
		}
	}
	
	private void updateStudent( int clazz, char section, Student s ) {
		final String update = "UPDATE students " + 
					    "SET class = ?," + 
					    "section = ?," + 
					    "rollNo = ?," + 
					    "firstName = ?," + 
					    "lastName = ?," +  
					    "WHERE UID = ?";
		
		Connection c = null;
		try {
			c = connPool.getConnection();
			PreparedStatement st = c.prepareStatement( update );
			st.setInt( 1, clazz );
			st.setString( 2, new String( new char[]{ section } ) );
			st.setInt( 3, s.getRollNo() );
			st.setString( 4, s.getFirstName() );
			st.setString( 5, s.getLastName() );
			st.setInt( 6, s.getUID() );
			st.executeUpdate();
			
		} catch( Exception e ) {
			log.error( "Unable to update student no. " + s.getUID() + " in the database", e );
		} finally {
			connPool.returnConnection( c );
		}
	}
	
	private void deleteStudent( Student s ) {
		final String update = "UPDATE students " + 
						"SET isEnabled = false " +   
						"WHERE UID = ?";
		
		Connection c = null;
		try {
			c = connPool.getConnection();
			PreparedStatement st = c.prepareStatement( update );
			st.setInt( 1, s.getUID() );
			st.executeUpdate();
			
		} catch( Exception e ) {
			log.error( "Unable to delete student no. " + s.getUID() + " in the database", e );
		} finally {
			connPool.returnConnection( c );
		}
	}
	
	public void createEntryInUpdateLog( String adminName ) {
		final String update = "INSERT INTO adminUpdateLog ( name )" + 
			 				  "VALUES ( ? )";
		Connection c = null;
		try {
			c = connPool.getConnection();
			PreparedStatement st = c.prepareStatement( update );
			st.setString( 1, adminName );
			st.executeUpdate();
			
		} catch( Exception ex ) {
			log.error( "Unable to update the adminLog", ex );
		}
	}

	public String[] getStudentDatabaseHeaders() {
		return new String[]{ "rollNo", "firstName", "lastName" }; 
	}
	
	public Student[] getStudentDatabaseData( int clazz, char section ) {
		final String query = 	
				"SELECT UID, rollNo, firstName, lastName FROM students " +
				"WHERE class = ? AND section = ? AND isEnabled = true";
		
		List<Student> students = new ArrayList<>();				 	
		Connection c = null;
		try {
			c = connPool.getConnection();
			PreparedStatement st = c.prepareStatement( query );
			st.setInt( 1, clazz );
			String str = new Character( section ).toString();
			st.setString( 2, str );
			ResultSet rs = st.executeQuery();
			while( rs.next() ) {
				int    UID       = rs.getInt( "UID" );
				int    rollNo    = rs.getInt( "rollNo" );
				String firstName = rs.getString( "firstName" );
				String lastName  = rs.getString( "lastName" );
				Student s = new Student( UID, rollNo, firstName, lastName );
				students.add( s );
			}
		} catch( Exception e ) {
			log.error( "Unable to get student database data for " + clazz + "-" + section, e );
		} finally {
			connPool.returnConnection( c );
		}
		
		return students.toArray( new Student[students.size()] );
	}

	public List<String> getEditableClasses() {
		final String query = "SELECT DISTINCT class, section FROM classes";
		List<String> editableClasses = new ArrayList<>(); 
		
		Connection c = null;
		try {
			c = connPool.getConnection();
			PreparedStatement st = c.prepareStatement( query );
			ResultSet rs = st.executeQuery();

			while( rs.next() ) {
				int  clazz   = rs.getInt( "class" );
				char section = rs.getString( "section" ).charAt(0);
				editableClasses.add( clazz + "-" + section );
			}
			
		} catch( Exception e ) {
			log.debug( "Unable to retrieve editable classes", e );
		} finally {
			connPool.returnConnection( c );
		}
		return editableClasses;
	}
	
}
