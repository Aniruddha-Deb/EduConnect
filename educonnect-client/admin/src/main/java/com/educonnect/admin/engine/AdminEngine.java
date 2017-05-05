package com.educonnect.admin.engine;

import java.awt.Cursor;
import java.util.Arrays;

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
	
	public AdminEngine( String serverIpAddress, int serverPort ) {
		super( Constants.TRUSTSTORE_PASSWD, Constants.TRUSTSTORE_LOC );
		clientAdapter = new SecureSocketNetworkAdapter( serverIpAddress,
														serverPort, 
														this );
		mainFrame = new MainFrame( this );
	}
	
	@Override
	public void login( String emailId, char[] password ) {
		mainFrame.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
		clientAdapter.connect();
		if( clientAdapter.isOpen() ) {
			
			LoginResponse r = (LoginResponse)clientAdapter.send( 
						new LoginRequest( emailId, password, ClientType.ADMIN ) );
			
			Arrays.fill( password, '0' );
			
			if( r.getLoginResult() != true ) {
				loginRequestFailed( r.getStatusText() );
				return;
			}
			else if( r.getStatusText() != null ) {
				UIUtils.showYesNoPrompt( r.getStatusText(), this );
			}
			setUserName( r.getLoginName() );
			loadEditPanel();			
		}
	}
	
	private void loginRequestFailed( String cause ) {
		UIUtils.showError( mainFrame, "Login request fail!\n" + cause );
		mainFrame.setCursor( Cursor.getDefaultCursor() );
		clientAdapter.shutdown();
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}
	
	private void setUserName( String userName ) {
		Constants.USER_NAME = userName;
	}
	
	private void loadEditPanel() {
		DatabaseAllClassesResponse dbResponse = (DatabaseAllClassesResponse)
				clientAdapter.send( new DatabaseAllClassesRequest() );
		mainFrame.getEditPanel().load( dbResponse );
		mainFrame.showPanel( UIConstants.EDIT_PANEL );	
		mainFrame.setCursor( Cursor.getDefaultCursor() );
	}
	
	@Override
	public void handleAsyncResponse( Response r ) {
		if( r instanceof DatabaseSingleClassResponse ) {
			mainFrame.getEditPanel().handleDatabaseSingleClassResponse( (DatabaseSingleClassResponse) r );
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
		disconnectAdapter();
		mainFrame.setVisible( false );
		mainFrame.dispose();
		System.exit( 0 );
	}
	
	private void disconnectAdapter() {
		if( clientAdapter != null && clientAdapter.isOpen() ) {
			clientAdapter.shutdown();
		}
	}
	
	@Override
	public void logout() {
		disconnectAdapter();
		mainFrame.showPanel( UIConstants.LOGIN_PANEL );
	}	
}
