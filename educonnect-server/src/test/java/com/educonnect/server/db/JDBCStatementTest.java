package com.educonnect.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCStatementTest {

	public static void main( String[] args ) {
		try {
			Class.forName( "com.mysql.cj.jdbc.Driver" ).newInstance();
			String url = "jdbc:mysql://localhost:3306/ec_tbs_camp?useSSL=false";
			String username = "root";
			String password = "bokor123";
			Connection connection = DriverManager.getConnection( url, username, password );
			
			String query = "DESCRIBE students";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery( query );
			
			while( rs.next() ) {
				if( !(rs.getString( "Field" ).equals( "passwd" ) || rs.getString( "Field" ).equals( "emailId" ) 
					|| rs.getString( "Field" ).equals( "image" ) || rs.getString( "Field" ).equals( "about" ) ) ) {
					System.out.println( rs.getString( "Field" ) );
				}
			}
		}
		catch (ClassNotFoundException ex) { ex.printStackTrace(); }
	    catch (IllegalAccessException ex) { ex.printStackTrace(); }
	    catch (InstantiationException ex) { ex.printStackTrace(); }
	    catch (SQLException ex)           { ex.printStackTrace(); }

	}

}
