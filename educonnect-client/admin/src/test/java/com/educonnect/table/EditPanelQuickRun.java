package com.educonnect.table;

import com.educonnect.admin.engine.AdminEngine;

public class EditPanelQuickRun {

	public static void main(String[] args) {
		AdminEngine e = new AdminEngine();
		e.start();
		e.login( "raseshr18@gmail.com" , "r@$e$#".toCharArray() );
	}
}
