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
import com.educonnect.common.message.Bean;
import com.educonnect.common.message.CommunicationConstants;
import com.educonnect.common.message.LoginBean;
import com.educonnect.common.message.payload.FailPayload;
import com.educonnect.common.message.payload.InfoPayload;
import com.educonnect.common.message.payload.Payload;
import com.educonnect.common.message.payload.db.DatabasePayload;
import com.educonnect.common.network.SecureSocketNetworkAdapter;

public class AdminEngine extends Engine{

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
		mainFrame = new MainFrame( this );
		clientAdapter = new SecureSocketNetworkAdapter( IP_ADDRESS, PORT, this );
	}
	
	@Override
	public void login( String emailId, char[] password ) {
		clientAdapter.connect();
		clientAdapter.send( new LoginBean( emailId, password, ClientType.ADMIN ) );
		
		System.out.println( "Sent login bean" );
	}
	
	private void loginRequestFailed( FailPayload p ) {
		JOptionPane.showMessageDialog( mainFrame, p.getCause() );
		clientAdapter.shutdown();
	}
	
	private void loginRequestSucceded( InfoPayload p ) {
		Constants.userName = CommunicationConstants.getName( p.getInfo() );
		System.out.println( "Showing edit panel" );
		mainFrame.showPanel( UIConstants.EDIT_PANEL );
	}
	
	@Override
	public void handle( Payload p ) {
		if( p instanceof InfoPayload ) {
			handleInfoPayload( (InfoPayload)p );
		}
		else if( p instanceof DatabasePayload ) {
			handleDatabasePayload( (DatabasePayload)p );
		}
		else if( p instanceof FailPayload ) {
			loginRequestFailed( (FailPayload)p );
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
		else if( ((InfoPayload) p).getInfo().startsWith( CommunicationConstants.NAME_INFO ) ) {
			System.out.println( "Showing stuff" );
			loginRequestSucceded( (InfoPayload)p );
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
	
	public Payload get() {
		return clientAdapter.get();
	}
	
	public void send( Bean b ) {
		clientAdapter.send( b );
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
