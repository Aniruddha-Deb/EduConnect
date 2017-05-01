package com.educonnect.server.payload;

import java.util.ArrayList;

import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.core.Request;
import com.educonnect.common.message.db.ClassOfStudents;
import com.educonnect.common.message.db.Student;
import com.educonnect.common.message.dbclass.DatabaseAllClassesRequest;
import com.educonnect.common.message.dbclass.DatabaseAllClassesResponse;
import com.educonnect.common.message.dbclass.DatabaseSingleClassRequest;
import com.educonnect.common.message.dbclass.DatabaseSingleClassResponse;
import com.educonnect.server.client.Client;
import com.educonnect.server.db.JDBCAdapter;

public class PayloadHandler {
	
	public static void handleResponse( Request r, Client c ) {
		if( r instanceof DatabaseAllClassesRequest ) {
			sendAllDBClasses( r, c );
		}
		else if( r instanceof DatabaseSingleClassRequest ) {
			sendDBClass( (DatabaseSingleClassRequest)r, c );
		}
	}
	
	private static void sendDBClass( DatabaseSingleClassRequest r, Client c ) {
		c.send( new DatabaseSingleClassResponse( ResponseStatus.OK, 
												 r.getUID(), 
												 new ClassOfStudents( 
													r.getClazz(), 
													r.getSection(), 
													JDBCAdapter.getInstance().getStudentDatabaseData( r.getClazz(), r.getSection() ) )
												  ) );
	}

	private static void sendAllDBClasses( Request r, Client client ) {
		String allDBs = JDBCAdapter.getInstance().getEditableClasses();
		String[] parts = allDBs.split( " " );

		ArrayList<ClassOfStudents> c = new ArrayList<>();
		
		for( String p : parts ) {
			int clazz = Integer.parseInt( p.split("-")[0] );
			char section = p.split( "-" )[1].charAt(0);
			Student[] students = JDBCAdapter.getInstance().getStudentDatabaseData( clazz, section );
			
			c.add( new ClassOfStudents( clazz, section, students ) );
		}
		
		client.send( new DatabaseAllClassesResponse( ResponseStatus.OK, 
													 r.getUID(), 
													 c.toArray( new ClassOfStudents[c.size()] ) ) );
	}
}
