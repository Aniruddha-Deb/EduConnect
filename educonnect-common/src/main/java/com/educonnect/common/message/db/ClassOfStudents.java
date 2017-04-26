package com.educonnect.common.message.db;

public class ClassOfStudents {

	private int clazz = -1;
	private char section = 'z';
	private Student[] students = null;
	
	public ClassOfStudents( int clazz, char section, Student[] students ) {
		this.clazz = clazz;
		this.section = section;
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
}
