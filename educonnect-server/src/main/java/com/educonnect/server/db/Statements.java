package com.educonnect.server.db;

public class Statements {
	
	static final String USER_NAME = "root";
	static final String PASSWORD  = "bokor123";
	static final String URL = "jdbc:mysql://localhost:3306/ec_tbs_camp?useSSL=false";
	static final String ADAPTER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	
	static final String GET_ADMIN_CLIENT = "SELECT UID FROM admins "
										 + "WHERE emailId = ? "
										 + "AND passwd = AES_ENCRYPT( ?, ? ) ";

	static final String GET_STUDENT_CLIENT = "SELECT UID FROM students "
										   + "WHERE emailId = ? "
										   + "AND passwd = AES_ENCRYPT( ?, ? ) ";
	
	static final String GET_ADMIN_NAME = "SELECT name FROM admins "
									   + "WHERE UID = ?";
	
	static final String GET_STUDENT_NAME = "SELECT firstName, lastName FROM students "
										 + "WHERE UID = ?";
	
	static final String CREATE_STUDENT = "INSERT INTO students " + 
			   							 "(class, section, rollNo, firstName, lastName) " + 
			   							 "VALUES ( ?, ?, ?, ?, ? )";  

	static final String UPDATE_STUDENT = "UPDATE students " + 
									     "SET class = ?," + 
									     "section = ?," + 
									     "rollNo = ?," + 
									     "firstName = ?," + 
									     "lastName = ?," +  
									     "WHERE UID = ?";
	
	static final String DELETE_STUDENT = "UPDATE students " + 
			   							 "SET isEnabled = false " +   
			   							 "WHERE UID = ?";

	static final String GET_STUDENTS_FROM_CLASS = 
			"SELECT UID, rollNo, firstName, lastName FROM students " +
			"WHERE class = ? AND section = ? AND isEnabled = true";
	
	static final String EDITABLE_CLASSES = "SELECT DISTINCT class, section FROM classes";
}
