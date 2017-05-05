package com.educonnect.server.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.educonnect.common.client.ClientType;
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

	private static final Logger log = Logger.getLogger( ClientHandler.class );
	
	private static List<Client> clients = new ArrayList<Client>();
	
	private static BufferedReader reader = null;
	private static BufferedWriter writer = null;
	
	public static void handle( Socket s ) throws IOException {
		
		reader = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		writer = new BufferedWriter( new OutputStreamWriter( s.getOutputStream() ) );
		
		Request r = (Request)NetworkUtils.readMessage( reader );
		
		if( r instanceof LoginRequest ) {
			LoginRequest lr = (LoginRequest)r;
			if( lr.getClientType().equals( ClientType.ADMIN ) ) {
				log.debug( "An Admin sent a loginRequest with emailId " + lr.getUserId() );
			}
			else {
				log.debug( "A Student sent a loginRequest with emailId " + lr.getUserId() );				
			}
			logInUser( lr, s );
		}
		else {
			log.debug( "The request sent was not a login request. Ignoring." );
		}
	}
	
	private static void logInUser( LoginRequest r, Socket s ) {
		LoginRequest loginRequest = (LoginRequest)r;
		int UID = getUID( loginRequest );
		
		if( UID == -1 ) {
			log.debug( "Client is not registered with system. Sending fail response." );
			tellClientAuthenticationFailed( loginRequest, "Admin not registered with system" );
		}
		else {
			if( isAdminAlreadyLoggedOn( UID ) ) {
				log.debug( "Client is an admin who is already logged on. Sending fail response." );
				tellClientAuthenticationFailed( loginRequest, "Admin is already logged on" );
			}
			else if( loginRequest.getClientType().equals( ClientType.ADMIN ) ) {
				log.debug( "Logging in admin " + JDBCAdapter.getInstance().getAdminName( UID ) );
				clients.add( new AdminClient( s, UID, r.getUID() ) );
			}
			else {
				log.debug( "Logging in student " + JDBCAdapter.getInstance().getStudentName( UID ) );
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
	
	private static void tellClientAuthenticationFailed( LoginRequest r, String cause ) {
		try {
			writer.write( 
					Serializer.serialize( 
						new LoginResponse( 
							r.getUID(),
							false
						)
						.withStatusText( cause )
					)
				);
			writer.flush();
			writer.write( Serializer.serialize( new ShutdownResponse( r.getUID() ) ) );
			writer.flush();
		} catch ( IOException e ) {
			log.error( "Could not tell client that authentication failed", e );
		}
	}
	
	public static void remove( Client client ) {
		for( Client c : clients ) {
			if( c.equals( client ) ) {
				log.debug( "Client " + c.getClientName() + " is logging off." );
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
	
	private static boolean isAdminAlreadyLoggedOn( int adminUID ) {
		for( Client c : clients ) {
			if( c.getUID() == adminUID ) {
				return true;
			}
		}		
		return false;
	}
}
