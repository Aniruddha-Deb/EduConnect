package com.educonnect.admin.engine;

import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.MainFrame;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.CommunicationConstants;
import com.educonnect.common.bean.LoginBean;
import com.educonnect.common.bean.payload.FailPayload;
import com.educonnect.common.bean.payload.InfoPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.bean.payload.db.DatabasePayload;
import com.educonnect.common.client.ClientType;
import com.educonnect.common.engine.Engine;
import com.educonnect.common.network.SecureSocketNetworkAdapter;

public class AdminEngine extends Engine{

	private MainFrame mainFrame = null;
	private SecureSocketNetworkAdapter adapter = null;
	
	private static final String IP_ADDRESS = "127.0.0.1";
	private static final int    PORT       = 1132;
	private static final String TRUSTSTORE_PASSWD   = "public";
	private static final String TRUSTSTORE_LOCATION = 
										"src/main/resources/client.truststore";
	
	private static ServerSocket ss = null;
		
	public AdminEngine() {
		super( TRUSTSTORE_PASSWD, TRUSTSTORE_LOCATION );
		setInstanceRunning();
		mainFrame = new MainFrame( this );
		adapter = new SecureSocketNetworkAdapter( IP_ADDRESS, PORT, this );
	}
	
	@Override
	public void login( String emailId, char[] password ) {
		adapter.connect();
		adapter.send( new LoginBean( emailId, password, ClientType.ADMIN ) );
		System.out.println( "Sent login bean" );
	}
	
	private void loginRequestFailed( FailPayload p ) {
		JOptionPane.showMessageDialog( mainFrame, p.getCause() );
		adapter.shutdown();
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
	
	private void setInstanceRunning() {
	    try {
	        ss = new ServerSocket( 11132 );
	      }
	      catch ( IOException ex ) {
	        JOptionPane.showMessageDialog( mainFrame, "Another instance of this \n"
	        										+ "application is already running" );
	        System.exit( -1 );
	      }
	}
	
	private void setInstanceStopped() {
		if( ss != null ) {
			try {
				ss.close();
			} catch( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleDatabasePayload( DatabasePayload p ) {
		mainFrame.getEditPanel().display( p );
	}
	
	public Payload get() {
		return adapter.get();
	}
	
	public void send( Bean b ) {
		adapter.send( b );
	}
	
	@Override
	public void start() {
		mainFrame.display();
	}

	@Override
	public void shutdown() {
		if( adapter != null ) {
			disconnectAdapter();
		}
		setInstanceStopped();
		mainFrame.setVisible( false );
		mainFrame.dispose();
		System.exit( 0 );
	}
	
	private void disconnectAdapter() {
		if( adapter.isOpen() ) {
			adapter.shutdown();
			while( adapter.getReceiverThread().isAlive() ) {
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
