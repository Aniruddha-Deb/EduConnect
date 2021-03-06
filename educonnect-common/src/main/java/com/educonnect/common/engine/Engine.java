package com.educonnect.common.engine;

import com.educonnect.common.message.core.Response;

/**
 * The Engine is the orchestrator of both the client side and server side programs. 
 * It serves as the link between the UI and the Adapter and helps in relaying 
 * messages between them. It also coordinates the startup and shutdown of both 
 * the UI and the adapter.</p>
 * 
 * @author Sensei
 *
 */
public abstract class Engine {
	
	/**
	 * The password of the truststore / keystore the Adapter needs to set
	 */
	private String passwd   = null;
	
	/**
	 * The location of the truststore / keystore the Adapter needs to set 
	 */
	private String location = null;
	
	/**
	 * The standard constructor used for creating an engine
	 * 
	 * @param passwd The password of the TrustStore or KeyStore
	 * @param location The location of the TrustStore or KeyStore
	 */
	protected Engine( String passwd, String location ) {
		this.location = location;
		this.passwd = passwd;
	}
	
	public String getPasswd() {
		return passwd;
	}
	
	public String getLocation() {
		return location;
	}
	
	/**
	 * This method is usually called by the Adapter when it receives a response 
	 * from the socket. It hands over custody of the response to the engine, which 
	 * can do whatever it wants with the response. This has to be overriden by 
	 * the subclasses of Engine.
	 * 
	 * @param p The payload received to be handled by the engine.
	 */
	public abstract void handleAsyncResponse( Response r );
	
	/**
	 * This method is the method called by the entry point of the application 
	 * and contains the neccessary statements required to initialize and start 
	 * the Adapter and the Engine.
	 */
	public abstract void start();
	
	/**
	 * This method logs in a user when he supplies his email ID and password.
	 * 
	 * @param emailId
	 * @param password
	 */
	public abstract void login( String emailId, char[] password ) throws Exception;
	
	/**
	 * This method (on the client side) is called by the UI MainFrame when the 
	 * close button is pressed. It tends to shutdown the receiver thread and the 
	 * UI before shutting the entire program down. On the server side, it is 
	 * configured to eject all clients and shut them down. After having done so, 
	 * the server main thread can close.
	 */
	public abstract void shutdown();
	
	/**
	 * This method logs out a user 
	 */
	public abstract void logout();
	
}
