package com.educonnect.admin.engine;

import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.MainFrame;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.common.client.ClientType;
import com.educonnect.common.engine.Engine;
import com.educonnect.common.message.core.Response;
import com.educonnect.common.message.dbclass.DatabaseAllClassesRequest;
import com.educonnect.common.message.dbclass.DatabaseAllClassesResponse;
import com.educonnect.common.message.dbclass.DatabaseSingleClassResponse;
import com.educonnect.common.message.login.LoginRequest;
import com.educonnect.common.message.login.LoginResponse;
import com.educonnect.common.network.SecureSocketNetworkAdapter;

public class AdminEngine extends Engine {

	private MainFrame mainFrame = null;
	private SecureSocketNetworkAdapter clientAdapter = null;
	
	private static ServerSocket singleInstanceSocket = null;
		
	public AdminEngine() {
		super( Constants.TRUSTSTORE_PASSWD, Constants.TRUSTSTORE_LOC );
		setCurrentInstanceRunning();
		clientAdapter = new SecureSocketNetworkAdapter( Constants.SERVER_IP_ADDRESS,
														Constants.SERVER_PORT, 
														this );
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

		Constants.USER_NAME = r.getStatusText();
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
	public void handleAsyncResponse( Response r ) {
		if( r instanceof DatabaseSingleClassResponse ) {
			mainFrame.getEditPanel().display( (DatabaseSingleClassResponse) r );
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
		}
	}

	@Override
	public void logout() {
		disconnectAdapter();
		mainFrame.showPanel( UIConstants.LOGIN_PANEL );
	}	
}
