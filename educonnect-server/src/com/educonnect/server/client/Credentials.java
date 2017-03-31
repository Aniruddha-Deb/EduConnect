package com.educonnect.server.client;

public class Credentials {

	// TODO init user credentials only with their roll numbers and scratch the 
	// name from an excel sheet within the system using org.apache.poi
	
	private int rollNo = 0;
	
	public Credentials( int rollNo )  {
		this.rollNo = rollNo;
	}
	
	public int getRollNo() {
		return rollNo;
	}

}
