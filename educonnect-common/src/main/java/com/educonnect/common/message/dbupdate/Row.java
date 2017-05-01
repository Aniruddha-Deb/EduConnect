package com.educonnect.common.message.dbupdate;

import com.educonnect.common.message.dbclass.Student;

public class Row {

	public enum RowAction {
		UPDATE, CREATE
	}
	
	private RowAction action = null;
	private Student student = null;
	private int classOfStudent = -1;
	private char sectionOfStudent = 'z';
	
	public Row( RowAction action, int classOfStudent, char sectionOfStudent, 
													Student student ) {
		this.action = action;
		this.student = student;
		this.classOfStudent = classOfStudent;
		this.sectionOfStudent = sectionOfStudent;
	}
	
	public RowAction getAction() {
		return action;
	}
	
	public Student getStudent() {
		return student;
	}
	
	public int getClassOfStudent() {
		return classOfStudent;
	}
	
	public char getSectionOfStudent() {
		return sectionOfStudent;
	}
}
