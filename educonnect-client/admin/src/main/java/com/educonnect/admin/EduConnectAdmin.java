package com.educonnect.admin;

import com.educonnect.admin.engine.AdminEngine;

public class EduConnectAdmin {
	
	public static void main( String[] args ) {
		new AdminEngine( 
				Constants.SERVER_IP_ADDRESS, Constants.SERVER_PORT ).start();
	}

}
