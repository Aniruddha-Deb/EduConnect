package com.educonnect.admin.engine;

import javax.swing.JOptionPane;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.MainFrame;
import com.educonnect.admin.ui.UIConstants;
import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.CommunicationConstants;
import com.educonnect.common.bean.LoginBean;
import com.educonnect.common.bean.payload.DatabasePayload;
import com.educonnect.common.bean.payload.FailPayload;
import com.educonnect.common.bean.payload.InfoPayload;
import com.educonnect.common.bean.payload.Payload;
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
		
	public AdminEngine() {
		super( TRUSTSTORE_PASSWD, TRUSTSTORE_LOCATION );	
		mainFrame = new MainFrame( this );
		adapter = new SecureSocketNetworkAdapter( IP_ADDRESS, PORT, this );
	}
	
	public void sendLoginRequest( String emailId, char[] password ) {
		adapter.connect();
		adapter.send( new LoginBean( emailId, password, ClientType.ADMIN ) );
		Payload p = adapter.get();
		
		if( p instanceof FailPayload ) {
			loginRequestFailed( (FailPayload)p );
		}
		else if( p instanceof InfoPayload ) {
			if( ((InfoPayload) p).getInfo().startsWith( CommunicationConstants.NAME_INFO ) ) {
				loginRequestSucceded( (InfoPayload)p );
			}
		}
	}
	
	private void loginRequestFailed( FailPayload p ) {
		JOptionPane.showMessageDialog( mainFrame, p.getCause() );
		adapter.shutdown();
	}
	
	private void loginRequestSucceded( InfoPayload p ) {
		Constants.userName = CommunicationConstants.getName( p.getInfo() );
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
		if( adapter.isConnected() ) {
			adapter.shutdown();
			while( adapter.getReceiverThread().isAlive() ) {
				try {
					Thread.sleep( 10 );
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
			}
		}
		mainFrame.setVisible( false );
		mainFrame.dispose();
		System.exit( 0 );
	}	
}
