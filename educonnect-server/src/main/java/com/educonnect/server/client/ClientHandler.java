package com.educonnect.server.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.educonnect.common.bean.FailBean;
import com.educonnect.common.bean.payload.LoginPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.client.ClientType;
import com.educonnect.common.parser.Parser;
import com.educonnect.common.serializer.Serializer;
import com.educonnect.server.db.JDBCAdapter;

public class ClientHandler {
	
	private static List<Client> clients = new ArrayList<>();
	
	public static void handle( Socket s ) throws IOException{
		
		BufferedReader reader = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( s.getOutputStream() ) );
		
		Payload p = Parser.parse( reader.readLine() );
		
		if( p instanceof LoginPayload ) {
			LoginPayload lp = (LoginPayload)p;
			String emailId = lp.getEmailId();
			char[] password = lp.getPassword();
			ClientType clientType = lp.getClientType();
			
			int UID = JDBCAdapter.getInstance().getClient( emailId, password, clientType );
			System.out.println( UID );
			if( UID == -1 ) {
				writer.write( Serializer.serialize( new FailBean( "Admin not registered with system" ) ) );
				writer.flush();
				System.out.println( "Wrote the fail bean" );
			}
			else {
				clients.add( new Client( s, UID, clientType ) );
			}
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

	public static void forceShutAllClients() {
		for( Client c : clients ) {
			c.forceShutdown();
		}
	}
}
