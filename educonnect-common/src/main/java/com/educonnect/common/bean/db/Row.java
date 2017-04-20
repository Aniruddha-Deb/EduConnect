package com.educonnect.common.bean.db;

public class Row {
	
	private int UID = -1;
	private Student student = null;
	
	public Row( int UID, Student student ) {
		this.UID = UID;
		this.student = student;
	}

	public int getUID() {
		return UID;
	}
	
	public Student getStudent() {
		return student;
	}
}
