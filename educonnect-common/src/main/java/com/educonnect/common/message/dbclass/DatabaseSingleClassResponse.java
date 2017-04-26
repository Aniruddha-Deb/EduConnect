package com.educonnect.common.message.dbclass;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.core.Response;
import com.educonnect.common.message.db.ClassOfStudents;

public class DatabaseSingleClassResponse extends Response {

	private static final long serialVersionUID = 236175482531669939L;
	
	private ClassOfStudents classOfStudents = null; 
	
	public DatabaseSingleClassResponse( ResponseStatus status, String requestUID, 
								  ClassOfStudents c ) {
		super( MessageType.MT_DB_SINGLE_CLASS_RES, status, requestUID );
		this.classOfStudents = c;
	}

	public ClassOfStudents getStudents() {
		return classOfStudents;
	}
}
