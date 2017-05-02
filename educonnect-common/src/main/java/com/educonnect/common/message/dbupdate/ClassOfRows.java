package com.educonnect.common.message.dbupdate;

public class ClassOfRows {

	private Row[] rows = null;
	private int   clazz = -1;
	private char  section = 'z';
	
	public ClassOfRows( Row[] rows, int clazz, char section ) {
		this.clazz = clazz;
		this.section = section;
		this.rows = rows;
	}
	
	public int getClazz() {
		return clazz;
	}
	
	public char getSection() {
		return section;
	}
	
	public Row[] getRows() {
		return rows;
	}
}
