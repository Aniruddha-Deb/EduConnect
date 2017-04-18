package com.educonnect.server.payload;

import com.educonnect.common.bean.CommunicationConstants;
import com.educonnect.common.bean.DatabaseBean;
import com.educonnect.common.bean.payload.InfoPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.client.ClientType;
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
		
		String[] headers = JDBCAdapter.getInstance().getStudentDatabaseHeaders();
		String[][] data  = JDBCAdapter.getInstance().getStudentDatabaseData( 
								Integer.parseInt( databaseParts[0] ),
								databaseParts[1].charAt(0) );
		
		c.send( new DatabaseBean( headers, data ) );
	}

}
