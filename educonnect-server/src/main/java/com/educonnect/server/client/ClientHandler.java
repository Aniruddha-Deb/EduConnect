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
import com.educonnect.common.parser.Parser;
import com.educonnect.common.serializer.Serializer;
import com.educonnect.server.db.JDBCAdapter;

public class ClientHandler {
	
	private static List<Client> clients = new ArrayList<>();
	
	private static BufferedReader reader = null;
	private static BufferedWriter writer = null;
	
	public static void handle( Socket s ) throws IOException{
		
		reader = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		writer = new BufferedWriter( new OutputStreamWriter( s.getOutputStream() ) );
		
		Request r = (Request)Parser.parse( readHeader(), readPayload() );
		
		if( r instanceof LoginRequest ) {
			LoginRequest lr = (LoginRequest)r;
			
			String emailId = lr.getUserId();
			char[] password = lr.getPassword();
			ClientType clientType = lr.getClientType();
			
			int UID = JDBCAdapter.getInstance().getClient( emailId, password, clientType );
			System.out.println( UID );
			if( UID == -1 ) {
				writer.write( 
					Serializer.serialize( 
						new LoginResponse( 
							ResponseStatus.SERVER_ERROR, 
							lr.getUID(),
							false
						)
						.withStatusText( "Admin not registered with system" )
					)
				);
				writer.flush();
				System.out.println( "Wrote the fail bean" );
			}
			else {
				clients.add( new Client( s, UID, clientType ) );
			}
		}
	}
	
	static String readHeader() {
		String header = null;
		try {
			int headerLength = Integer.parseInt( reader.readLine() );
			header = new String();
			for( int i=0; i<headerLength; i++ ) {
				char charRead = (char)reader.read();
				header += charRead;
			}
			reader.readLine();
			System.out.println( header );
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		return header;
	}
	
	static String readPayload() {
		String payload = null;
		try {
			int payloadLength = Integer.parseInt( reader.readLine() );
			payload = new String();
			for( int i=0; i<payloadLength; i++ ) {
				char charRead = (char)reader.read();
				payload += charRead;
			}
			System.out.println( payload );
			reader.readLine();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		return payload;
	}

	
	public static void remove( Client client ) {
		for( Client c : clients ) {
			if( c.equals( client ) ) {
				clients.remove( c );
				break;
			}
		}
	}

}
