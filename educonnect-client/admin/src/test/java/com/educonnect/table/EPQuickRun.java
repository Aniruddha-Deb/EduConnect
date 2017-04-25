package com.educonnect.table;

import com.educonnect.admin.engine.AdminEngine;

public class EPQuickRun {
	
	public static void main(String[] args) {
		
		AdminEngine engine = new AdminEngine();
		engine.login( "aniruddha.deb.2002@gmail.com", "@n1ruddh@".toCharArray() );
		engine.start();
	}

}
