package com.educonnect.common.bean.payload;

public class RegisterPayload extends Payload {

	private int    clazz   = -1  ;
	private char   section = 'z' ;
	private int    rollNo  = -1  ;
	private String emailId = null;
	private String passwd  = null;
	
	public RegisterPayload( int clazz, char section, int rollNo, String emailId,
							String passwd ) {
		this.clazz   = clazz  ;
		this.section = section;
		this.rollNo  = rollNo ;
		this.emailId = emailId;
		this.passwd  = passwd ;
	}
	
	public int getClazz() {
		return clazz;
	}
	
	public char getSection() {
		return section;
	}
	
	public int getRollNo() {
		return rollNo;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public String getPasswd() {
		return passwd;
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
		
		int classHash   = clazz   == -1   ? 47 : new Integer( clazz ).hashCode();
		int sectionHash = section == 'z'  ? 53 : new Character( section ).hashCode();
		int rollNoHash  = rollNo  == -1   ? 59 : new Integer( rollNo ).hashCode();
		int emailIdHash = emailId == null ? 47 : emailId.hashCode();
		int passwdHash  = passwd  == null ? 47 : passwd.hashCode();
		
		return classHash + sectionHash + rollNoHash + emailIdHash + passwdHash;
	}
}
