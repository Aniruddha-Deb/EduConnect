package com.sensei.poc;

import java.sql.*;

public class JDBCExample {

	public static void main( String[] args ) {
		Connection conn = null;
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
			conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/playground",
												"root",
												"bokor123" );
			String query = "SELECT * FROM 9d_test";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery( query );
			
			System.out.println( "rollNo | firstName | lastName | passwd" );
			
			System.out.println( "------------------------" );
			while( rs.next() ) {
				
				int rollNo = rs.getInt( "rollNo" );
				String firstName = rs.getString( "firstName" );
				String lastName = rs.getString( "lastName" );
				String passwd = rs.getString( "passwd" );
				
				System.out.println( rollNo + " | " + firstName + " | " + lastName + " | "
						+ passwd);
			}
		} catch( Exception ex ) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
