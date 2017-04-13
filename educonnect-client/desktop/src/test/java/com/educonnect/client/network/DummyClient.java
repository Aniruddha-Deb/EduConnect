package com.educonnect.client.network;

import static com.educonnect.common.bean.BeanTests.CLASS;
import static com.educonnect.common.bean.BeanTests.EMAIL_ID;
import static com.educonnect.common.bean.BeanTests.IN_FILE_PATH;
import static com.educonnect.common.bean.BeanTests.PASSWORD;
import static com.educonnect.common.bean.BeanTests.ROLLNO;
import static com.educonnect.common.bean.BeanTests.SECTION;
import static com.educonnect.common.bean.BeanTests.SENDER;
import static com.educonnect.common.bean.BeanTests.TEXT;

import com.educonnect.common.bean.AuthBean;
import com.educonnect.common.bean.FileBean;
import com.educonnect.common.bean.LoginBean;
import com.educonnect.common.bean.TextBean;

public class DummyClient {

	public static void main(String[] args) throws InterruptedException{
		SecureSocketNetworkAdapter adapter = new SecureSocketNetworkAdapter( "127.0.0.1", 1132 );
		adapter.send( new LoginBean( CLASS, SECTION, ROLLNO ) );
		adapter.send( new AuthBean( EMAIL_ID, PASSWORD ) );
		adapter.send( new TextBean( SENDER, TEXT ) );
		adapter.send( new FileBean( IN_FILE_PATH ) );
		
		System.out.println( "Done" );
		adapter.shutdown();
		System.out.println( "Shutdown" );
	}
}
