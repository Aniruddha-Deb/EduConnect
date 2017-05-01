package com.educonnect.server.client.admin;

import java.net.Socket;
import java.util.List;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.login.LoginResponse;
import com.educonnect.server.client.Client;
import com.educonnect.server.client.ClientHandler;
import com.educonnect.server.db.JDBCAdapter;

public class AdminClient extends Client {

	public AdminClient( Socket socket, int UID, String requestUID ) {
		super( socket, UID, ClientType.ADMIN );
		super.clientName = JDBCAdapter.getInstance().getAdminName( UID );
		
		List<Client> loggedOnAdminClients = ClientHandler.getLoggedOnAdminClients();
		System.out.println( "got loggedOnAdminClients" );
		
		if( !( loggedOnAdminClients.isEmpty() ) ) {
			
			StringBuilder b = new StringBuilder( 
			"Some admins are already logged on to the system. This may result \n"
			+ "in conflicts while editing data. Do you want to continue?\n"
			+ "Logged in admins:\n" );
			
			for( Client c : loggedOnAdminClients ) {
				b.append( c.getClientName() + "\n" ); 
			}
			send( new LoginResponse( 
					ResponseStatus.OK, 
					requestUID,
					true
				).withLoginName( clientName )
				 .withStatusText( b.toString() )
			);

		}
		else {
			send( new LoginResponse( 
					ResponseStatus.OK, 
					requestUID,
					true
				).withLoginName( clientName ) );
		}
	}
}
