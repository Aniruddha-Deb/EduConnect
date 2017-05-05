package com.educonnect.server.client.student;

import java.net.Socket;

import org.apache.log4j.Logger;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.login.LoginResponse;
import com.educonnect.server.client.Client;
import com.educonnect.server.db.JDBCAdapter;

public class StudentClient extends Client {

	private static final Logger log = Logger.getLogger( StudentClient.class );
	
	public StudentClient( Socket socket, int UID ) {
		super( socket, UID, ClientType.STUDENT );
		super.clientName = JDBCAdapter.getInstance().getStudentName( UID );
		send( new LoginResponse( 
							null,
							true
			  ).withStatusText( clientName )
		);
		log.info( "Successfully logged in student " + super.getClientName() );
	}
}
