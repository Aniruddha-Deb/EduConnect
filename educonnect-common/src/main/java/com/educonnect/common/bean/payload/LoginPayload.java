package com.educonnect.common.bean.payload;

/**
 * A LoginPayload is a concrete implementation of a {@link Payload}. It contains 
 * all the required fields required for logging a user in.  
 * @author Sensei
 *
 */
public class LoginPayload extends Payload {
	
	private int  grade   = -1;
	private char section = 'Z';
	private int  rollNo  = -1;
	
	/**
	 * The standard constructor for creating a LoginPayload 
	 * 
	 * @param grade   The grade or class the student belongs to
	 * @param section The section the student belongs to
	 * @param rollNo  The roll number of the student in that class
	 */
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
		
		int gradeHash   = grade   == -1  ? 7  : new Integer( grade ).hashCode();
		int sectionHash = section == 'Z' ? 11 : new Character( section ).hashCode();
		int rollNoHash  = rollNo  == -1  ? 13 : new Integer( rollNo ).hashCode();
		
		return gradeHash + sectionHash + rollNoHash;
	}
}
