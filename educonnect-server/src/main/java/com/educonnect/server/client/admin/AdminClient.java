package com.educonnect.server.client.admin;

import java.net.Socket;
import java.util.List;

import org.apache.log4j.Logger;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.login.LoginResponse;
import com.educonnect.server.client.Client;
import com.educonnect.server.client.ClientHandler;
import com.educonnect.server.db.JDBCAdapter;

public class AdminClient extends Client {
	
	private static final Logger log = Logger.getLogger( AdminClient.class );

	public AdminClient( Socket socket, int UID, String requestUID ) {
		super( socket, UID, ClientType.ADMIN );
		super.clientName = JDBCAdapter.getInstance().getAdminName( UID );
		
		List<Client> loggedOnAdminClients = ClientHandler.getLoggedOnAdminClients();
		
		if( !( loggedOnAdminClients.isEmpty() ) ) {
			log.info( "Other admin clients are already logged on. Alerting admin." );
			alertClientThatOtherAdminsAreLoggedOn( loggedOnAdminClients, requestUID );
		}
		else {
			logOnAdminClient( requestUID );
			log.info( "Successfully logged in admin " + super.getClientName() );
		}
	}
	
	private void alertClientThatOtherAdminsAreLoggedOn( 
			List<Client> loggedOnAdminClients, String requestUID ) {
		StringBuilder b = new StringBuilder( 
			"Some admins are already logged on to the system. This may result \n"
			+ "in conflicts while editing data. Do you want to continue?\n"
			+ "Logged in admins:\n" );
		
		for( Client c : loggedOnAdminClients ) {
			b.append( c.getClientName() + "\n" ); 
		}
		send( new LoginResponse( 
				requestUID,
				true
			).withLoginName( clientName )
			 .withStatusText( b.toString() )
		);
	}
	
	private void logOnAdminClient( String requestUID ) {
		send( new LoginResponse( 
				requestUID,
				true
			).withLoginName( clientName ) );
	}
}
