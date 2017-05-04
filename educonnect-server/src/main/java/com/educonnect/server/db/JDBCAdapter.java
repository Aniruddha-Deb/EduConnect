package com.educonnect.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.dbclass.Student;
import com.educonnect.common.message.dbupdate.Row;
import com.educonnect.common.message.dbupdate.Row.RowAction;

public class JDBCAdapter {

	private static JDBCAdapter instance = null;
	private Connection connection = null;
	
	public static JDBCAdapter getInstance() {
		if( instance == null ) {
			instance = new JDBCAdapter();
		}
		return instance;
	}
	
	private JDBCAdapter() {
		try {
			Class.forName( "com.mysql.cj.jdbc.Driver" ).newInstance();
			String url = "jdbc:mysql://localhost:3306/ec_tbs_camp?useSSL=false";
			String username = "root";
			String password = "bokor123";
			connection = DriverManager.getConnection( url, username, password );
		}
		catch (ClassNotFoundException ex) { ex.printStackTrace(); }
	    catch (IllegalAccessException ex) { ex.printStackTrace(); }
	    catch (InstantiationException ex) { ex.printStackTrace(); }
	    catch (SQLException ex)           { ex.printStackTrace(); }
	}
	
	public int getClient( String emailId, char[] password, ClientType clientType ) {
		if( clientType.equals( ClientType.ADMIN ) ) {
			return getAdminClient( emailId, password );
		}
		else if( clientType.equals( ClientType.STUDENT ) ){
			return getStudentClient( emailId, password );
		}
		return -1;
	}

	private int getAdminClient( String emailId, char[] password ) {
		int adminClientUID = -1;
		String query = "SELECT UID FROM admins WHERE emailId='" + emailId + 
					   "' AND passwd=AES_ENCRYPT( '" + new String( password ) 
					   + "', '" + new String( password ) + "')";
					 	
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery( query );
			rs.next();
			try {
				adminClientUID = rs.getInt( "UID" );
			} catch( SQLException ex ) {
				adminClientUID = -1;
			}
			
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return adminClientUID;
	}
	
	public String getAdminName( int UID ) {
		String adminName = null;
		
		String query = "SELECT name FROM admins WHERE UID=" + UID;					 	
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery( query );
			rs.next();
			adminName = rs.getString( "name" );
			
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return adminName;		
	}
	
	public String getStudentName( int UID ) {
		String studentName = null;
		
		String query = "SELECT firstName, lastName FROM students WHERE UID=" + UID;					 	
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery( query );
			rs.next();
			studentName = rs.getString( "firstName" ) + " " + 
						  rs.getString( "lastName" );
			
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return studentName;		
	}

	public void updateRow( Row r, int clazz, char section ) {
		int classOfStudent = clazz;
		char sectionOfStudent = section;
		int rollNo = r.getStudent().getRollNo();
		int UID = r.getStudent().getUID();
		String firstName = r.getStudent().getFirstName();
		String lastName = r.getStudent().getLastName();
		
		if( r.getAction().equals( RowAction.CREATE ) ) {
			
			String query = "INSERT INTO students " + 
						   "(class, section, rollNo, firstName, lastName) " + 
						   "VALUES ( " + classOfStudent + " ,'" + 
						   				 sectionOfStudent + "' ," + 
						   				 rollNo + " ,'" + 
						   				 firstName + "' ,'" + 
						   				 lastName + "' )"; 
			try {
				Statement st = connection.createStatement();
				st.executeUpdate( query );
			} catch( SQLException e ) {
				e.printStackTrace();
			}
		}
		else if( r.getAction().equals( RowAction.UPDATE ) ){
			
			String query = "UPDATE students " + 
						   "SET class = " + classOfStudent + ", " + 
						   "section = '" + sectionOfStudent + "', " + 
						   "rollNo = " + rollNo + ", " + 
						   "firstName = '" + firstName + "', " + 
						   "lastName = '" + lastName + "' " +  
						   "WHERE UID = " + UID;
						   
			try {
				Statement st = connection.createStatement();
				st.executeUpdate( query );
			} catch( SQLException e ) {
				e.printStackTrace();
			}
		}
		else {
			String query = "UPDATE students " + 
					   "SET isEnabled=false " +   
					   "WHERE UID = " + UID;
					   
			try {
				Statement st = connection.createStatement();
				st.executeUpdate( query );
			} catch( SQLException e ) {
				e.printStackTrace();
			}			
		}
	}
	
	public String[] getStudentDatabaseHeaders() {
		return new String[]{ "rollNo", "firstName", "lastName" }; 
	}
	
	public Student[] getStudentDatabaseData( int clazz, char section ) {
		String query = "SELECT UID, rollNo, firstName, lastName FROM students WHERE "
				+ "class=" + clazz + " AND section='" + section + "' AND isEnabled=true";
		
		List<Student> students = new ArrayList<>();				 	
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery( query );
			
			while( rs.next() ) {
				int    UID       = rs.getInt( "UID" );
				int    rollNo    = rs.getInt( "rollNo" );
				String firstName = rs.getString( "firstName" );
				String lastName  = rs.getString( "lastName" );
				Student s = new Student( UID, rollNo, firstName, lastName );
				students.add( s );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return students.toArray( new Student[students.size()] );
	}
	
	private int getStudentClient( String emailId, char[] password ) {
		// TODO do this when students actually start using the server
		return 0;
	}

	public String getEditableClasses() {
		String query = "SELECT DISTINCT class, section FROM classes";
		StringBuilder b = new StringBuilder();
				 	
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery( query );

			while( rs.next() ) {
				int  clazz   = rs.getInt( "class" );
				char section = rs.getString( "section" ).charAt(0);
				b.append( clazz + "-" + section + " " );
			}
			
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return b.toString();
	}
	
}
