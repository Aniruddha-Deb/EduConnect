package com.educonnect.admin.engine;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.MainFrame;
import com.educonnect.admin.ui.panels.MainPanel;
import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.LoginBean;
import com.educonnect.common.bean.payload.DatabasePayload;
import com.educonnect.common.bean.payload.FailPayload;
import com.educonnect.common.bean.payload.FilePayload;
import com.educonnect.common.bean.payload.InfoPayload;
import com.educonnect.common.bean.payload.Payload;
import com.educonnect.common.client.ClientType;
import com.educonnect.common.engine.Engine;
import com.educonnect.common.network.SecureSocketNetworkAdapter;

public class AdminEngine extends Engine{

	private MainFrame mainFrame = null;
	private SecureSocketNetworkAdapter adapter = null;
	
	private static AdminEngine instance = null;
	
	private static final String IP_ADDRESS = "127.0.0.1";
	private static final int    PORT       = 1132;
	
	public AdminEngine() {
		super( "public", "src/main/resources/client.truststore" );	
		instance = this;
		mainFrame = new MainFrame();
		adapter = new SecureSocketNetworkAdapter( IP_ADDRESS, PORT, this );
	}
	
	public static AdminEngine getInstance() {
		return instance;
	}
	
	public void sendLoginRequest( String emailId, char[] password ) {
		adapter.connect();
		adapter.send( new LoginBean( emailId, password, ClientType.ADMIN ) );
		Payload p = adapter.get();
		System.out.println( "got p" );
		if( p instanceof FailPayload ) {
			mainFrame.alert( ((FailPayload) p).getCause() );
			adapter.shutdown();
		}
		else if( p instanceof InfoPayload ) {
			Constants.userName = ((InfoPayload) p).getInfo();
			mainFrame.getCardLayout().show( mainFrame.getMainPanel(), "editPanel" );
		}
	}
	
	@Override
	public void handle( Payload p ) {
		if( p instanceof InfoPayload ) {
			InfoPayload ip = (InfoPayload)p;
			if( ip.getInfo().startsWith( "DB headers:" ) ) {
				mainFrame.getEditPanel().load( new InfoPayload( 
						ip.getInfo().substring( 11 ) ) );
			}
		}
		else if( p instanceof DatabasePayload ) {
			mainFrame.getEditPanel().display( (DatabasePayload)p );
		}
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
