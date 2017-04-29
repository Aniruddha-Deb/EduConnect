package com.educonnect.server.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.educonnect.common.client.ClientType;
import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.core.Request;
import com.educonnect.common.message.login.LoginRequest;
import com.educonnect.common.message.login.LoginResponse;
import com.educonnect.common.message.shutdown.ShutdownResponse;
import com.educonnect.common.network.NetworkUtils;
import com.educonnect.common.serializer.Serializer;
import com.educonnect.server.client.admin.AdminClient;
import com.educonnect.server.client.student.StudentClient;
import com.educonnect.server.db.JDBCAdapter;

public class ClientHandler {
	
	private static List<Client> clients = new ArrayList<Client>();
	
	private static BufferedReader reader = null;
	private static BufferedWriter writer = null;
	
	public static void handle( Socket s ) throws IOException {
		
		System.out.println( "Client connected!" );
		reader = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		writer = new BufferedWriter( new OutputStreamWriter( s.getOutputStream() ) );
		
		Request r = (Request)NetworkUtils.readMessage( reader );
		
		if( r instanceof LoginRequest ) {
			logInUser( (LoginRequest)r, s );
		}
		else {
			// Ignore the request
		}
	}
	
	private static void logInUser( LoginRequest r, Socket s ) {
		LoginRequest loginRequest = (LoginRequest)r;
		int UID = getUID( loginRequest );
		
		if( UID == -1 ) {
			tellClientAuthenticationFailed( loginRequest );
		}
		else {
			if( loginRequest.getClientType().equals( ClientType.ADMIN ) ) {				
				clients.add( new AdminClient( s, UID ) );
			}
			else {
				clients.add( new StudentClient( s, UID ) );				
			}
		}
	}
	
	private static int getUID( LoginRequest r ) {
		String emailId = r.getUserId();
		char[] password = r.getPassword();
		ClientType clientType = r.getClientType();
		
		int UID = JDBCAdapter.getInstance().getClient( emailId, password, clientType );
		return UID;
	}
	
	private static void tellClientAuthenticationFailed( LoginRequest r ) {
		try {
			writer.write( 
					Serializer.serialize( 
						new LoginResponse( 
							ResponseStatus.SERVER_ERROR, 
							r.getUID(),
							false
						)
						.withStatusText( "Admin not registered with system" )
					)
				);
			writer.flush();
			writer.write( Serializer.serialize( new ShutdownResponse( r.getUID() ) ) );
			writer.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	public static void remove( Client client ) {
		for( Client c : clients ) {
			if( c.equals( client ) ) {
				clients.remove( c );
				break;
			}
		}
	}
	
	public static List<Client> getLoggedOnAdminClients() {
		List<Client> loggedOnAdminClients = new ArrayList<>();
		
		for( Client c : clients ) {
			if( c.getClientType().equals( ClientType.ADMIN ) ) {
				loggedOnAdminClients.add( c );
			}
		}
		
		return loggedOnAdminClients;
	}
}
