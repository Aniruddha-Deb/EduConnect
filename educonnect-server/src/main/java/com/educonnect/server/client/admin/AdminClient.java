package com.educonnect.server.client.admin;

import java.net.Socket;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.login.LoginResponse;
import com.educonnect.server.client.Client;
import com.educonnect.server.db.JDBCAdapter;

public class AdminClient extends Client {

	public AdminClient( Socket socket, int UID ) {
		super( socket, UID, ClientType.ADMIN );
		super.clientName = JDBCAdapter.getInstance().getAdminName( UID );
		send( new LoginResponse( 
							ResponseStatus.PROCESS_OK, 
							null,
							true
			  ).withStatusText( clientName )
		);
	}
}
