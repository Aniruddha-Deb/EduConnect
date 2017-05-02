package com.educonnect.common.message.dbupdate;

import com.educonnect.common.message.dbclass.Student;

public class Row {

	public enum RowAction {
		UPDATE, CREATE, DELETE
	}
	
	private RowAction action = null;
	private Student student = null;
	
	public Row( RowAction action, Student student ) {
		this.action = action;
		this.student = student;
	}
	
	public RowAction getAction() {
		return action;
	}
	
	public Student getStudent() {
		return student;
	}	
}
