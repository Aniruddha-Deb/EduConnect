package com.educonnect.common.bean.payload.db;

import java.util.Arrays;

import com.educonnect.common.bean.db.Student;
import com.educonnect.common.bean.payload.Payload;

public class DatabasePayload extends Payload {

	private int       clazz    = -1;
	private char      section  = 'z';
	private Student[] students = null;

	public DatabasePayload( int clazz, char section, Student[] students ) {
		this.clazz    = clazz;
		this.section  = section;
		this.students = students;
	}

	public int getClazz() {
		return clazz;
	}
	
	public char getSection() {
		return section;
	}
	
	public Student[] getStudents() {
		return students;
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

		int classHash    = clazz    == -1   ? 67 : new Integer( clazz ).hashCode();
		int sectionHash  = section  == 'z'  ? 71 : new Character( section ).hashCode();
		int studentsHash = students == null ? 73 : Arrays.hashCode( students );
				
		return classHash + sectionHash + studentsHash;
	}

}
