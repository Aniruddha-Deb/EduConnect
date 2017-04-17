package com.educonnect.server.payload;

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
		info = info.toLowerCase();
		System.out.println( "Got infoPayload" );
		if( c.getClientType().equals( ClientType.ADMIN ) ) {
			System.out.println( "Payload sent by admin" );
			System.out.println( info );
			if( info.startsWith( "requesting table " ) ) {
				System.out.println( "Client " + c.getClientName() + " " + info );
				String[] parts = info.split( " " );
				String requestedTable = parts[parts.length-1];
				
				sendRequestedTableTo( c, requestedTable );
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
