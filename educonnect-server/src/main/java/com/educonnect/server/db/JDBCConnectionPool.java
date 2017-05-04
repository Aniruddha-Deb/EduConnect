package com.educonnect.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class JDBCConnectionPool {
	
	private String url      = null ;
	private String user     = null ;
	private String password = null ;
	
	private GenericObjectPool<Connection> connectionPool = null ;
	
	public JDBCConnectionPool( String driver, String url, String user, String password ) 
	    throws Exception {
	    
	    Class.forName( driver ).newInstance() ;
	    
	    this.url      = url ;
	    this.user     = user ;
	    this.password = password ;

	    connectionPool = new GenericObjectPool<Connection>( new PooledObjectFactory<Connection>() {

			@Override
			public void activateObject(PooledObject<Connection> arg0) throws Exception {
			}
			
			@Override
			public void destroyObject(PooledObject<Connection> arg0) throws Exception {
				arg0.getObject().close();
			}
				
			@Override
			public PooledObject<Connection> makeObject() throws Exception {
				return new DefaultPooledObject<Connection>( 
						DriverManager.getConnection( 
								JDBCConnectionPool.this.url, 
								JDBCConnectionPool.this.user, 
								JDBCConnectionPool.this.password ) );
			}

			@Override
			public void passivateObject( PooledObject<Connection> arg0 ) throws Exception {
			}

			@Override
			public boolean validateObject(PooledObject<Connection> arg0) {
				return false;
			}
	    
	    } );
	    connectionPool.setMaxTotal( 5 );
	}
	
	public Connection getConnection() throws Exception {
	    return connectionPool.borrowObject() ;
	}
	
	public void returnConnection( Connection conn ) {
	    try {
	        connectionPool.returnObject( conn ) ;
	    } 
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	}	
}
