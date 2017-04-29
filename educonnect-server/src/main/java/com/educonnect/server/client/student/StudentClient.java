package com.educonnect.server.client.student;

import java.net.Socket;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.login.LoginResponse;
import com.educonnect.server.client.Client;
import com.educonnect.server.db.JDBCAdapter;

public class StudentClient extends Client {

	public StudentClient( Socket socket, int UID ) {
		super( socket, UID, ClientType.STUDENT );
		super.clientName = JDBCAdapter.getInstance().getStudentName( UID );
		send( new LoginResponse( 
							ResponseStatus.PROCESS_OK, 
							null,
							true
			  ).withStatusText( clientName )
		);
	}
}
