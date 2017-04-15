package com.educonnect.admin.engine;

import com.educonnect.admin.Constants;
import com.educonnect.admin.ui.MainFrame;
import com.educonnect.common.bean.Bean;
import com.educonnect.common.bean.LoginBean;
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
		instance = this;
		mainFrame = new MainFrame();
		adapter = new SecureSocketNetworkAdapter( IP_ADDRESS, PORT, this );
	}
	
	public static AdminEngine getInstance() {
		return instance;
	}
	
	public void sendLoginRequest( String emailId, String password, ClientType clientType ) {
		adapter.send( new LoginBean( emailId, password, clientType ) );
		Payload p = adapter.get();
		if( p instanceof FailPayload ) {
			mainFrame.alert( ((FailPayload) p).getCause() );
		}
		else if( p instanceof InfoPayload ) {
			Constants.userName = ((InfoPayload) p).getInfo();
			mainFrame.alert( "Hiiiiii" );
		}
	}
	
	@Override
	public void handle( Payload p ) {
		if( p instanceof FilePayload ) {
			((FilePayload) p).unloadFile( Constants.XLSX_FILE_PATH );
		}
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
		adapter.shutdown();
		while( adapter.getReceiverThread().isAlive() ) {
			try {
				Thread.sleep( 10 );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
		mainFrame.setVisible( false );
		mainFrame.dispose();
		System.exit( 0 );
	}
	
	public static void main(String[] args) {
		new AdminEngine().start();
	}
}
