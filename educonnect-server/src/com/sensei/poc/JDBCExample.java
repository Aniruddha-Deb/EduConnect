package com.sensei.poc;

import java.sql.*;

public class JDBCExample {

	Connection sqlConnection = null;
	
	public JDBCExample() {
		connectToDatabase();
		runTests();
	}
	
	private void connectToDatabase() {
		try {
			Class.forName( "com.mysql.jdbc.Driver").newInstance();
		} catch( Exception ex ) {
			System.out.println( "JDBC driver not found" );
			System.exit( -1 );
		}
		
		System.out.println( "JDBC driver registered" );

		try {
			sqlConnection = DriverManager.getConnection( 
						"jdbc:mysql://localhost:3306/9D", "root", "bokor123" );
		} catch( SQLException ex ) {
			ex.printStackTrace();
		}
		
		System.out.println( "Successfully connected to SQL server on port "
				+ "3306 with userID = root and password = bokor123" );
		System.out.println( "---------------------------------------" );
	}
	
	private void runTests() {
		String query = "SELECT * FROM 9D";
		
		try {
			Statement st = sqlConnection.createStatement();
			ResultSet rs = st.executeQuery( query );
			
			while( rs.next() ) {
				int rollNo = rs.getInt( "rollNo" );
				String firstName = rs.getString( "firstName" );
				String lastName  = rs.getString( "lastName" );
				String passwd    = rs.getString( "passwd" );
				System.out.println( "rollNo    = " + rollNo );
				System.out.println( "firstName = " + firstName );
				System.out.println( "lastName  = " + lastName );
				System.out.println( "passwd    = " + passwd );
				System.out.println( "-------------------------------" );
			}
		} catch( SQLException ex ) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new JDBCExample();
	}

}
