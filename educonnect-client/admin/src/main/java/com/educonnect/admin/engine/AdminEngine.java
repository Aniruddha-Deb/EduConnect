package com.educonnect.admin.engine;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.MainFrame;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.common.client.ClientType;
import com.educonnect.common.engine.Engine;
import com.educonnect.common.message.CommunicationConstants;
import com.educonnect.common.message.dbclass.DatabaseAllClassesRequest;
import com.educonnect.common.message.dbclass.DatabaseAllClassesResponse;
import com.educonnect.common.message.login.LoginRequest;
import com.educonnect.common.message.login.LoginResponse;
import com.educonnect.common.message.payload.InfoPayload;
import com.educonnect.common.message.payload.Payload;
import com.educonnect.common.message.payload.db.DatabasePayload;
import com.educonnect.common.network.SecureSocketNetworkAdapter;

public class AdminEngine extends Engine {

	private MainFrame mainFrame = null;
	private SecureSocketNetworkAdapter clientAdapter = null;
	
	private static final String IP_ADDRESS = "192.168.0.100";
	private static final int    PORT       = 1132;
	private static final String TRUSTSTORE_PASSWD   = "public";
	private static final String TRUSTSTORE_LOCATION = 
										System.getProperty( "user.home" ) + 
										File.separator + "client.truststore";
	
	private static ServerSocket singleInstanceSocket = null;
		
	public AdminEngine() {
		super( TRUSTSTORE_PASSWD, TRUSTSTORE_LOCATION );
		setCurrentInstanceRunning();
		clientAdapter = new SecureSocketNetworkAdapter( IP_ADDRESS, PORT, this );
		mainFrame = new MainFrame( this );
	}
	
	@Override
	public void login( String emailId, char[] password ) {
		clientAdapter.connect();
		LoginResponse r = (LoginResponse)clientAdapter.send( 
					new LoginRequest( emailId, password, ClientType.ADMIN ) );
		
		if( !r.getLoginResult() ) {
			loginRequestFailed( r.getStatusText() );
			return;
		}

		Constants.userName = r.getStatusText();
		DatabaseAllClassesResponse dbResponse = (DatabaseAllClassesResponse)
						clientAdapter.send( new DatabaseAllClassesRequest() );
		mainFrame.getEditPanel().load( dbResponse );
		mainFrame.showPanel( UIConstants.EDIT_PANEL );
		
	}
	
	private void loginRequestFailed( String cause ) {
		JOptionPane.showMessageDialog( mainFrame, cause );
		clientAdapter.shutdown();
	}
	
	@Override
	public void handle( Payload p ) {
		if( p instanceof InfoPayload ) {
			handleInfoPayload( (InfoPayload)p );
		}
		else if( p instanceof DatabasePayload ) {
			handleDatabasePayload( (DatabasePayload)p );
		}
	}
	
	private void handleInfoPayload( InfoPayload p ) {
		String info = p.getInfo();
		if( info.startsWith( CommunicationConstants.DB_HEADER_INFO ) ) {
			System.out.println( CommunicationConstants.getDBHeaders( info ) );
			mainFrame.getEditPanel().load( 
				CommunicationConstants.getDBHeaders( info )
			);
		}
	}
	
	private void setCurrentInstanceRunning() {
	    try {
	        singleInstanceSocket = new ServerSocket( 11132 );
	      }
	      catch ( IOException ex ) {
	        JOptionPane.showMessageDialog( mainFrame, "Another instance of this \n"
	        										+ "application is already running" );
	        System.exit( -1 );
	      }
	}
	
	private void setInstanceStopped() {
		if( singleInstanceSocket != null ) {
			try {
				singleInstanceSocket.close();
			} catch( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleDatabasePayload( DatabasePayload p ) {
		mainFrame.getEditPanel().display( p );
	}
	
	public SecureSocketNetworkAdapter getClientAdapter() {
		return clientAdapter;
	}
	
	@Override
	public void start() {
		mainFrame.display();
	}

	@Override
	public void shutdown() {
		if( clientAdapter != null ) {
			disconnectAdapter();
		}
		setInstanceStopped();
		mainFrame.setVisible( false );
		mainFrame.dispose();
		System.exit( 0 );
	}
	
	private void disconnectAdapter() {
		if( clientAdapter.isOpen() ) {
			clientAdapter.shutdown();
			while( clientAdapter.getReceiverThread().isAlive() ) {
				try {
					Thread.sleep( 10 );
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void logout() {
		disconnectAdapter();
		mainFrame.showPanel( UIConstants.LOGIN_PANEL );
	}	
}
