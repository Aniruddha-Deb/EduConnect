package com.educonnect.common.bean.payload;

public class LoginPayload extends Payload {
	
	private int  grade   = -1;
	private char section = 'Z';
	private int  rollNo  = -1;
	
	public LoginPayload( int grade, char section, int rollNo ) {
		this.grade   = grade;
		this.section = section;
		this.rollNo  = rollNo;
	}

	public int getGrade() {
		return grade;
	}
	
	public char getSection() {
		return section;
	}
	
	public int getRollNo() {
		return rollNo;
	}
}
