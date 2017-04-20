package com.educonnect.common.bean.db;

public class Student {
	
	private int rollNo = -1;
	
	private String firstName = null;
	private String lastName  = null;
	
	public Student( int rollNo, String firstName, String lastName ) {
		this.rollNo = rollNo;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public int getRollNo() {
		return rollNo;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
}
