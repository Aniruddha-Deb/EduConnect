package com.educonnect.admin.engine;

import java.io.IOException;
import java.net.ServerSocket;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.MainFrame;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.admin.ui.util.UIUtils;
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
//		setCurrentInstanceRunning();
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
		else if( r.getStatusText() != null ) {
			UIUtils.showYesNoPrompt( r.getStatusText(), this );
			setUserName( r.getLoginName() );
			loadEditPanel();
		}
		else {
			setUserName( r.getLoginName() );
			loadEditPanel();			
		}
	}
	
	private void loginRequestFailed( String cause ) {
		UIUtils.showError( Constants.ERROR_LOGIN_REQUEST_FAILED, "Login request fail!\n" + 
																  cause );
		clientAdapter.shutdown();
	}
	
	private void setUserName( String userName ) {
		Constants.USER_NAME = userName;
	}
	
	private void loadEditPanel() {
		DatabaseAllClassesResponse dbResponse = (DatabaseAllClassesResponse)
				clientAdapter.send( new DatabaseAllClassesRequest() );
		mainFrame.getEditPanel().load( dbResponse );
		mainFrame.showPanel( UIConstants.EDIT_PANEL );	
	}
	
	@Override
	public boolean handleAsyncResponse( Response r ) {
		if( r instanceof DatabaseSingleClassResponse ) {
			mainFrame.getEditPanel().handleDatabaseSingleClassResponse( (DatabaseSingleClassResponse) r );
			return true;
		}
		return false;
	}
	
	private void setCurrentInstanceRunning() {
	    try {
	        singleInstanceSocket = new ServerSocket( 11132 );
	      }
	      catch ( IOException ex ) {
	    	  UIUtils.showError( Constants.ERROR_INSTANCE_ALREADY_RUNNING, 
	    			 "Another instance of this application is already running" );
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
		setInstanceStopped();
		disconnectAdapter();
		mainFrame.setVisible( false );
		mainFrame.dispose();
		System.exit( 0 );
	}
	
	private void disconnectAdapter() {
		if( clientAdapter != null && clientAdapter.isOpen() ) {
			System.out.println( "Shutting down adapter" );
			clientAdapter.shutdown();
		}
		else {
			System.out.println( "Adapter is null, no need to shutdown" );
		}
	}
	
	@Override
	public void logout() {
		disconnectAdapter();
		mainFrame.showPanel( UIConstants.LOGIN_PANEL );
	}	
}
