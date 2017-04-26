package com.educonnect.server.payload;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.CommunicationConstants;
import com.educonnect.common.message.db.DatabaseBean;
import com.educonnect.common.message.db.Student;
import com.educonnect.common.message.payload.InfoPayload;
import com.educonnect.common.message.payload.Payload;
import com.educonnect.server.client.Client;
import com.educonnect.server.db.JDBCAdapter;

public class PayloadHandler {
	
	public static void handlePayload( Payload p, Client c ) {
		if( p instanceof InfoPayload ) {
			String info = ((InfoPayload) p).getInfo();
			handleInfoPayload( info, c );
		}
	}
	
	private static void handleInfoPayload( String info, Client c ) {
		if( c.getClientType().equals( ClientType.ADMIN ) ) {
			
			if( info.startsWith( CommunicationConstants.REQUEST_TABLE_INFO ) ) {
				sendRequestedTableTo( c, CommunicationConstants.getRequestedTable( info ) );
			}
		}
	}

	private static void sendRequestedTableTo( Client c, String database ) {
		String[] databaseParts = database.split( "-" );
		
		int clazz = Integer.parseInt( databaseParts[0] );
		char section = databaseParts[1].charAt(0);
		Student[] students = JDBCAdapter.getInstance().getStudentDatabaseData( 
							 clazz, section );
				
		c.send( new DatabaseBean( clazz, section, students ) );
	}

}
