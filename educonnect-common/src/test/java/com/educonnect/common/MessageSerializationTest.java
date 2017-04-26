package com.educonnect.common;

import org.junit.Test;

import com.educonnect.common.message.ResponseStatus;
import com.educonnect.common.message.core.Response;
import com.educonnect.common.message.login.LoginResponse;

public class MessageSerializationTest {

	@Test
	public void test() {
//		Message msg = new LoginRequest( "Aniruddha", "password" ) ;
		Response msg = new LoginResponse( ResponseStatus.PROCESS_OK, 
				                         "134f43ed-4273-4180-86f3-ca8633e2c380" ) ;
		msg.setStatusText( "Welcome Aniruddha." ) ;
		StringBuilder msgStr = new StringBuilder() ;
		
		msgStr.append( msg.getMessageHeaderAsJSON().length() )
		      .append( "\n" ) ;
		msgStr.append( msg.getMessageHeaderAsJSON() )
		      .append( "\n" ) ;
		msgStr.append( msg.getMessageBodyAsJSON().length() ) 
		      .append( "\n" ) ;
		msgStr.append( msg.getMessageBodyAsJSON() ) 
		      .append( "\n" ) ;
		
		System.out.println( msgStr ) ;
	}
}
