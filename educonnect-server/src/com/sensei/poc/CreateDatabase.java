package com.sensei.poc;

import java.sql.*;

public class CreateDatabase {

	public static void main( String[] args ) {
		Connection conn = null;
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
			conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306", 
												"root", 
												"bokor123" );
			Statement s = conn.createStatement();
//			int result  = s.executeUpdate( "CREATE DATABASE test;" );
			int result = s.executeUpdate( "USE test;" );
			result = s.executeUpdate( "CREATE TABLE 9D ("
									+ "rollNo INT,"
									+ "firstName VARCHAR(20),"
									+ "lastName VARCHAR(20),"
									+ "passwd CHAR(32),"
									+ "email VARCHAR(320),"
									+ "bio VARCHAR(100),"
									+ "profilePic BLOB"
									+ ");" );
			result = s.executeUpdate( "DESCRIBE 9D" );

			System.out.println( result );
			
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
}
