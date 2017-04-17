package com.educonnect.server.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.educonnect.common.client.ClientType;

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
	
	public String[] getStudentDatabaseHeaders() {
		return new String[]{ "rollNo", "firstName", "lastName" }; 
	}
	
	public String[][] getStudentDatabaseData( int clazz, char section ) {
		String query = "SELECT rollNo, firstName, lastName FROM students WHERE "
				+ "class=" + clazz + " AND section='" + section + "'";
		
		String[][] data = new String[100][3];				 	
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery( query );
			
			for( int i=0; rs.next(); i++ ) {
				data[i][0] = rs.getInt( "rollNo" ) + "" ;
				System.out.println( data[i][0] );
				data[i][1] = rs.getString( "firstName" );
				System.out.println( data[i][1] );
				data[i][2] = rs.getString( "lastName" );
				System.out.println( data[i][2] );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		return data;
	}
	
	private int getStudentClient( String emailId, char[] password ) {
		// TODO do this when students actually start using the server
		return 0;
	}

	public String getEditableClasses() {
		String query = "SELECT DISTINCT class, section FROM students";
		StringBuilder b = new StringBuilder( "DB headers:" );
				 	
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
