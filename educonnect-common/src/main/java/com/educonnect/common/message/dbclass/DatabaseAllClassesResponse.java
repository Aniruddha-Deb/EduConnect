package com.educonnect.common.message.dbclass;

import com.educonnect.common.message.MessageType;
import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.core.Response;
import com.educonnect.common.message.db.ClassOfStudents;

public class DatabaseAllClassesResponse extends Response {

	private static final long serialVersionUID = -1036461871363658953L;
	
	private ClassOfStudents[] classes = null;

	public DatabaseAllClassesResponse( ResponseStatus status, 
									   String requestUID, 
									   ClassOfStudents[] classes ) {
		super( MessageType.MT_DB_ALL_CLASSES_RES, status, requestUID );
		this.classes = classes;
	}
	
	public ClassOfStudents[] getClasses() {
		return classes;
	}
}
