package com.educonnect.common.message.dbclass;

import java.io.Serializable;

public class Student implements Serializable{
	
	private static final long serialVersionUID = 8437060009660430333L;

	private int rollNo = -1;
	private int UID = -1;
	private String firstName = null;
	private String lastName  = null;
	
	public Student( int UID, int rollNo, String firstName, String lastName ) {
		this.rollNo = rollNo;
		this.UID = UID;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public int getRollNo() {
		return rollNo;
	}
	
	public int getUID() {
		return UID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
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
	
	@Override
	public String toString() {
		return rollNo + " " + firstName + " " + lastName;
	}
}
