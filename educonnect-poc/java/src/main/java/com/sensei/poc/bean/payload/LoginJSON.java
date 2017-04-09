package com.sensei.poc.bean.payload;

public class LoginJSON extends JSONPayload {
	
	private int  grade   = -1;
	private char section = 'Z';
	private int  rollNo  = -1;
	
	public LoginJSON( int grade, char section, int rollNo ) {
		if( grade < 9 || grade > 12 ) {
			throw new IllegalArgumentException( "Grade not supported currently" );
		}
		else {
			this.grade = grade;
		}
		
		if( section > 'E' || section < 'A' ) {
			throw new IllegalArgumentException( "Section not supported currently" );			
		}
		else {
			this.section = section;
		}
		
		if( rollNo < 1 ) {
			throw new IllegalArgumentException( "Roll number cannot be less than 1" );
		}
		else {
			this.rollNo = rollNo;
		}		
	}

	public int getRollNo() {
		return rollNo;
	}
	
	public char getSection() {
		return section;
	}
	
	public int getGrade() {
		return grade;
	}
}
