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
	
	@Override
	public boolean equals( Object obj ) {
		if( obj == this ) return true;
		if( obj == null ) return false;
		if( obj.getClass() == this.getClass() && obj.hashCode() == this.hashCode() ) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {

		int rollNoHash    = rollNo    == -1   ? 53 : new Integer( rollNo ).hashCode();
		int firstNameHash = firstName == null ? 59 : firstName.hashCode();
		int lastNameHash  = lastName  == null ? 61 : lastName.hashCode();
		
		return rollNoHash + firstNameHash + lastNameHash;
	}
}
