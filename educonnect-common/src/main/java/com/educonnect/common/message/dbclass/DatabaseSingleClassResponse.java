package com.educonnect.common.message.dbclass;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.core.Response;

public class DatabaseSingleClassResponse extends Response {

	private static final long serialVersionUID = 236175482531669939L;
	
	private ClassOfStudents classOfStudents = null; 
	
	public DatabaseSingleClassResponse( String requestUID, 
								  ClassOfStudents c ) {
		super( MessageType.MT_DB_SINGLE_CLASS_RES, requestUID );
		this.classOfStudents = c;
	}

	public ClassOfStudents getClassOfStudents() {
		return classOfStudents;
	}
}
